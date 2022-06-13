package com.mal.cloud.uploadFiles.data

import com.mal.cloud.auth.data.component.AuthenticatedUserComponent
import com.mal.cloud.auth.data.table.Usr
import com.mal.cloud.uploadFiles.data.component.DetermineFileType
import com.mal.cloud.uploadFiles.data.configuration.LocationData
import com.mal.cloud.uploadFiles.data.database.dbRepository.FileInfoRepository
import com.mal.cloud.uploadFiles.data.database.table.FileInfo
import com.mal.cloud.uploadFiles.data.exceptions.FileExistentException
import com.mal.cloud.uploadFiles.data.exceptions.StorageException
import com.mal.cloud.uploadFiles.domain.entitiy.FileEntity
import com.mal.cloud.uploadFiles.domain.repository.StorageRepository
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class StorageService(
    private val authenticatedUserComponent: AuthenticatedUserComponent,
    private val determineFileType: DetermineFileType,
    private val fileInfoRepository: FileInfoRepository,
    locationData: LocationData,
) : StorageRepository {
    private val location = Paths.get(locationData.storageLocation)

    override fun storeFile(file: MultipartFile): FileEntity {
        val user = authenticatedUserComponent.getAuthenticatedUser()

        if (file.isEmpty) {
            throw StorageException("Failed to store empty file.")
        }
        val destinationFile: Path = getPath(user, file.originalFilename!!)
        val hash = saveFile(file, destinationFile)

        val fileInfo = fileInfoRepository.save(
            FileInfo(
                file.originalFilename ?: "",
                hash,
                user.userId!!,
                determineFileType.getFileType(file.originalFilename!!)
            )
        )

        return FileEntity(fileInfo)
    }

    override fun deleteFile(fileHash: String): Boolean {
        val file = fileInfoRepository.findFileInfoByFileHash(fileHash).getOrNull(0)!!
        val user = authenticatedUserComponent.getAuthenticatedUser()

        Files.delete(getPath(user, file.originFileName))
        fileInfoRepository.deleteById(file.fileId!!)
        return true
    }

    private fun saveFile(file: MultipartFile, destinationFile: Path): String {
        try {
            try {
                Files.createDirectories(destinationFile)
            } catch (e: IOException) {
                throw FileExistentException()
            }
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING
                )
                return DigestUtils.md5DigestAsHex(file.bytes)
            }
        } catch (ex: IOException) {
            throw StorageException("Failed to store file.", ex)
        }
    }

    private fun getPath(
        user: Usr,
        fileOriginName: String
    ): Path {
        return location.resolve(
            Paths.get("${user.userId}/$fileOriginName")
        ).normalize().toAbsolutePath()
    }
}