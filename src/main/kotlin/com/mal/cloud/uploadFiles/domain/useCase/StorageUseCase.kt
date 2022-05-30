package com.mal.cloud.uploadFiles.domain.useCase

import com.mal.cloud.main.error.DefaultError
import com.mal.cloud.uploadFiles.data.StorageService
import com.mal.cloud.uploadFiles.data.exceptions.AccessFileDeniedException
import com.mal.cloud.uploadFiles.data.exceptions.FileExistentException
import com.mal.cloud.uploadFiles.data.exceptions.StorageException
import com.mal.cloud.uploadFiles.data.exceptions.StorageFileNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class StorageUseCase(
    private val storageService: StorageService
) {
    fun storeFile(file: MultipartFile): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(storageService.storeFile(file).toStorageFile())
        } catch (e: StorageException) {
            val status = HttpStatus.CONFLICT
            ResponseEntity<Any>(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = status.value(),
                    error = e.message ?: "null",
                    message = "Файл существет"
                ), status
            )
        } catch (e: FileExistentException) {
            val status = HttpStatus.FORBIDDEN
            ResponseEntity<Any>(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = status.value(),
                    error = e.message ?: "null",
                    message = "Файл существет"
                ), status
            )
        }
    }

    fun loadFile(fileName: String): ResponseEntity<Any> {
        return try {
            val storageEntity = storageService.loadFile(fileName)

            return ResponseEntity.ok()
                .header("content-disposition", "inline; filename=\"" + storageEntity.resource.filename + "\"")
                .contentLength(storageEntity.resource.contentLength())
                .contentType(storageEntity.mediaType)
                .body(storageEntity.resource)
        } catch (e: StorageFileNotFoundException) {
            val status = HttpStatus.NOT_FOUND
            ResponseEntity<Any>(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = status.value(),
                    error = e.message ?: "null",
                    message = "Файл не найден"
                ), status
            )
        } catch (e: AccessFileDeniedException) {
            val status = HttpStatus.FORBIDDEN
            ResponseEntity<Any>(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = status.value(),
                    error = e.message ?: "null",
                    message = "Ошибка доступа"
                ), status
            )
        }

    }
}