package com.mal.cloud.uploadFiles.domain.useCase

import com.mal.cloud.main.error.DefaultError
import com.mal.cloud.uploadFiles.data.exceptions.FileExistentException
import com.mal.cloud.uploadFiles.data.exceptions.StorageException
import com.mal.cloud.uploadFiles.domain.repository.StorageRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class StorageUseCase(
    private val storageService: StorageRepository
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

    fun deleteFile(fileHash: String): ResponseEntity<Any> {
        return try {
            storageService.deleteFile(fileHash)
            ResponseEntity<Any>(
                HttpStatus.OK
            )
        } catch (e: Exception) {
            val status = HttpStatus.BAD_REQUEST
            ResponseEntity<Any>(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = status.value(),
                    error = e.message ?: "null",
                    message = "Ошибка удаления"
                ), status
            )
        }
    }
}