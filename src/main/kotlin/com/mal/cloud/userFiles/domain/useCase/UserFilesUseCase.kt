package com.mal.cloud.userFiles.domain.useCase

import com.mal.cloud.main.error.DefaultError
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
}