package com.mal.cloud.userFiles.domain.useCase

import com.mal.cloud.main.error.DefaultError
import com.mal.cloud.uploadFiles.data.exceptions.AccessFileDeniedException
import com.mal.cloud.userFiles.exceptions.StorageFileNotFoundException
import com.mal.cloud.userFiles.domain.repository.UserFilesRepository
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserFilesUseCase(
    private val userFilesRepository: UserFilesRepository
) {

    fun getUserFiles(pageable: Pageable): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(userFilesRepository.getUserFiles(pageable).toUserFile())
        } catch (e: Exception) {
            val status = HttpStatus.BAD_REQUEST
            ResponseEntity<Any>(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = status.value(),
                    error = e.message ?: "null",
                    message = "Неизвестная ошибка"
                ), status
            )
        }
    }

    fun getUserFile(fileHash: String): ResponseEntity<Any> {
        return try {
            val storageEntity = userFilesRepository.getUserFile(fileHash)

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