package com.mal.cloud.auth.presentation.exceptionHandler

import com.mal.cloud.auth.data.exceptions.UserAlreadyExistException
import com.mal.cloud.auth.data.exceptions.UserInvalidValuesException
import com.mal.cloud.main.error.DefaultError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*


@ControllerAdvice(annotations = [AuthExceptionHandler::class])
class AuthExceptionHandlingController {

    @ExceptionHandler(UserInvalidValuesException::class)
    fun authError(exception: UserInvalidValuesException): ResponseEntity<DefaultError> {
        val status = HttpStatus.FORBIDDEN
        return ResponseEntity<DefaultError>(
            DefaultError(
                timestamp = Date().toInstant().toString(),
                status = status.value(),
                error = exception.message ?: "null",
                message = "Неверный логин или пароль"
            ), status
        )
    }

    @ExceptionHandler(UserAlreadyExistException::class)
    fun userExist(exception: UserAlreadyExistException): ResponseEntity<DefaultError> {
        val status = HttpStatus.CONFLICT
        return ResponseEntity<DefaultError>(
            DefaultError(
                timestamp = Date().toInstant().toString(),
                status = status.value(),
                error = exception.message ?: "null",
                message = "Такой пользователь уже существует"
            ), status
        )
    }
}