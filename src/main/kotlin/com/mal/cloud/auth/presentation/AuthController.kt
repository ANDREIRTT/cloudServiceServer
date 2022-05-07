package com.mal.cloud.auth.presentation

import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.domain.dto.AuthData
import com.mal.cloud.auth.domain.useCase.AuthUseCase
import com.mal.cloud.auth.presentation.exceptionHandler.AuthExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
@AuthExceptionHandler
class AuthController(
    private val authUseCase: AuthUseCase
) {
    @RequestMapping(path = ["/register"], consumes = ["multipart/form-data"], method = [RequestMethod.POST])
    fun register(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam userRole: UserRole
    ): AuthData {
        return authUseCase.register(username, password, userRole)
    }

    @RequestMapping(path = ["/login"], consumes = ["multipart/form-data"], method = [RequestMethod.POST])
    fun login(
        @RequestParam username: String,
        @RequestParam password: String
    ): AuthData {
        return authUseCase.login(username, password)
    }
}