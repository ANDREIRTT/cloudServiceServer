package com.mal.cloud.uploadFiles.data

import com.mal.cloud.auth.data.component.AuthenticatedUserComponent
import com.mal.cloud.uploadFiles.data.component.DetermineFileType
import com.mal.cloud.uploadFiles.data.configuration.LocationData
import com.mal.cloud.uploadFiles.data.database.dbRepository.FileInfoRepository
import com.mal.cloud.uploadFiles.data.database.table.FileInfo
import com.mal.cloud.uploadFiles.data.exceptions.AccessFileDeniedException
import com.mal.cloud.uploadFiles.data.exceptions.FileExistentException
import com.mal.cloud.uploadFiles.data.exceptions.StorageException
import com.mal.cloud.uploadFiles.data.exceptions.StorageFileNotFoundException
import com.mal.cloud.uploadFiles.domain.entitiy.LoadFileEntity
import com.mal.cloud.uploadFiles.domain.entitiy.SaveFileEntity
import com.mal.cloud.uploadFiles.domain.repository.StorageRepository
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
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

    override fun storeFile(file: MultipartFile): SaveFileEntity {
        val user = authenticatedUserComponent.getAuthenticatedUser()

        if (file.isEmpty) {
            throw StorageException("Failed to store empty file.")
        }
        val destinationFile: Path = location.resolve(
            Paths.get(
                "${user.userId}/" +
                        "${file.originalFilename}"
            )
        ).normalize().toAbsolutePath()
        val hash = saveFile(file, destinationFile)

        val fileInfo = fileInfoRepository.save(
            FileInfo(
                file.originalFilename ?: "",
                hash,
                user.userId!!,
                determineFileType.getFileType(file.originalFilename!!)
            )
        )

        return SaveFileEntity(fileInfo)
    }

    override fun loadFile(fileHash: String): LoadFileEntity {
        val user = authenticatedUserComponent.getAuthenticatedUser()
        val fileInfo = fileInfoRepository.findFileInfoByFileHash(fileHash).getOrNull(0)
            ?: throw StorageFileNotFoundException(
                "Could not found file: $fileHash"
            )
        if (user.userId != fileInfo.userId) {
            throw AccessFileDeniedException("content not available for ${user.username}")
        }

        return try {
            val path: Path = location.resolve("${user.userId}/${fileInfo.originFileName}").normalize()
            val resource: Resource = UrlResource(path.toUri())
            if (resource.exists() || resource.isReadable) {
                LoadFileEntity(
                    resource,
                    determineFileType.getMediaType(fileInfo.originFileName)
                )
            } else {
                throw StorageFileNotFoundException(
                    "Could not read file: $fileHash"
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $fileHash", e)
        }
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
                return DigestUtils.md5DigestAsHex(inputStream)
            }
        } catch (ex: IOException) {
            throw StorageException("Failed to store file.", ex)
        }
    }
}