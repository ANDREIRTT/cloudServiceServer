package com.mal.cloud.auth.presentation

import com.mal.cloud.auth.domain.entity.UserEntity
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.domain.useCase.AuthUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
class AuthController(
    private val authUseCase: AuthUseCase
) {
    @RequestMapping(path = ["/register"], consumes = ["multipart/form-data"], method = [RequestMethod.POST])
    fun register(
        @RequestParam username: String,
        @RequestParam password: String
    ): UserEntity {
        return authUseCase.register(username, password, UserRole.ROLE_USER)
    }

    @RequestMapping(path = ["/login"], consumes = ["multipart/form-data"], method = [RequestMethod.POST])
    fun login(
        @RequestParam username: String,
        @RequestParam password: String
    ): UserEntity {
        return authUseCase.login(username, password)
    }

    @RequestMapping(path = ["/create"], consumes = ["multipart/form-data"], method = [RequestMethod.POST])
    fun createAdmin(
        @RequestParam username: String,
        @RequestParam password: String
    ): UserEntity {
        return authUseCase.createAdmin(username, password)
    }
}