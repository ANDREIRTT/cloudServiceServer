package com.mal.cloud.userFiles.data

import com.mal.cloud.auth.data.component.AuthenticatedUserComponent
import com.mal.cloud.uploadFiles.data.component.DetermineFileType
import com.mal.cloud.uploadFiles.data.configuration.LocationData
import com.mal.cloud.uploadFiles.data.database.dbRepository.FileInfoRepository
import com.mal.cloud.uploadFiles.data.exceptions.AccessFileDeniedException
import com.mal.cloud.userFiles.exceptions.StorageFileNotFoundException
import com.mal.cloud.userFiles.domain.entity.UserFileEntity
import com.mal.cloud.userFiles.domain.entity.UserFilesEntity
import com.mal.cloud.userFiles.domain.repository.UserFilesRepository
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.net.MalformedURLException
import java.nio.file.Path
import java.nio.file.Paths

@Service
class UserFilesService(
    private val fileInfoRepository: FileInfoRepository,
    private val authenticatedUserComponent: AuthenticatedUserComponent,
    private val determineFileType: DetermineFileType,
    locationData: LocationData,
) : UserFilesRepository {

    private val location = Paths.get(locationData.storageLocation)
    override fun getUserFiles(pageable: Pageable): UserFilesEntity {
        val user = authenticatedUserComponent.getAuthenticatedUser()

        val files =
            fileInfoRepository.findAllUserFiles(user.userId!!, PageRequest.of(pageable.pageNumber, pageable.pageSize))
        return UserFilesEntity(
            files.pageable.pageNumber,
            files.pageable.pageSize,
            files.isLast,
            files.content
        )
    }

    override fun getUserFile(fileHash: String): UserFileEntity {
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
                UserFileEntity(
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
}