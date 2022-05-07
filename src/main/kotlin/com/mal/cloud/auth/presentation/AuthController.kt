package com.mal.cloud.auth.presentation

import com.mal.cloud.auth.domain.entity.UserEntity
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.domain.dto.AuthData
import com.mal.cloud.auth.domain.dto.UserInfo
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

    @RequestMapping(path = ["/info"], method = [RequestMethod.POST])
    fun info(): UserInfo {
        return authUseCase.userInfo()
    }
}