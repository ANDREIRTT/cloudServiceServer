package com.mal.cloud.auth.domain.useCase

import com.mal.cloud.auth.data.exceptions.UserAlreadyExistException
import com.mal.cloud.auth.data.exceptions.UserInvalidValuesException
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.domain.repository.AuthRepository
import com.mal.cloud.main.error.DefaultError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthUseCase(
    private val authRepository: AuthRepository
) {
    fun register(username: String, password: String, userRole: UserRole): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(authRepository.register(username, password, userRole).toAuthData())
        } catch (e: UserAlreadyExistException) {
            val status = HttpStatus.CONFLICT
            ResponseEntity<Any>(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = status.value(),
                    error = e.message ?: "null",
                    message = "Такой пользователь уже существует"
                ), status
            )
        } catch (e: UserInvalidValuesException) {
            val status = HttpStatus.UNAUTHORIZED
            ResponseEntity<Any>(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = status.value(),
                    error = e.message ?: "null",
                    message = "Не действительный логин или пароль"
                ), status
            )
        }
    }

    fun login(username: String, password: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(authRepository.login(username, password).toAuthData())
        } catch (e: UserInvalidValuesException) {
            val status = HttpStatus.UNAUTHORIZED
            ResponseEntity<Any>(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = status.value(),
                    error = e.message ?: "null",
                    message = "Неверный логин или пароль"
                ), status
            )
        }
    }
}