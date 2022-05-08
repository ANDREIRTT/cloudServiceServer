package com.mal.cloud.uploadFiles.data

import com.mal.cloud.auth.data.dbRepository.UserDbRepository
import com.mal.cloud.auth.data.table.Usr
import com.mal.cloud.uploadFiles.data.component.MediaTypeComponent
import com.mal.cloud.uploadFiles.data.configuration.LocationData
import com.mal.cloud.uploadFiles.data.exceptions.StorageException
import com.mal.cloud.uploadFiles.data.exceptions.StorageFileNotFoundException
import com.mal.cloud.uploadFiles.domain.entitiy.StorageEntity
import com.mal.cloud.uploadFiles.domain.repository.StorageRepository
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class StorageService(
    private val userDbRepository: UserDbRepository,
    private val locationData: LocationData,
    private val mediaTypeComponent: MediaTypeComponent
) : StorageRepository {
    private val path = Paths.get(locationData.storageLocation)

    override fun storeFile(file: MultipartFile) {
        val userName = SecurityContextHolder.getContext().authentication.principal as String
        val user = userDbRepository.findUserByUsername(userName)!!

        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file.")
            }

            val destinationFile: Path = path.resolve(
                Paths.get(
                    "${user.userId}/" +
                            "${file.hashCode()}${file.originalFilename}"
                )
            ).normalize().toAbsolutePath()
            Files.createDirectories(destinationFile)
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
        } catch (e: IOException) {
            throw StorageException("Failed to store file.", e)
        }
    }

    override fun loadFile(userId: Long, filename: String): StorageEntity {
        val user = when (val security = SecurityContextHolder.getContext().authentication.principal) {
            is String -> {
                userDbRepository.findUserByUsername(security)!!
            }
            is Usr -> {
                security
            }
            else -> {
                null
            }
        }
        return try {
            val path: Path = path.resolve("${user?.userId}/$filename").normalize()
            val resource: Resource = UrlResource(path.toUri())
            if (resource.exists() || resource.isReadable) {
                StorageEntity(
                    resource,
                    mediaTypeComponent.getMediaTypeForFileName(filename)
                )
            } else {
                throw StorageFileNotFoundException(
                    "Could not read file: $filename"
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $filename", e)
        }
    }
}