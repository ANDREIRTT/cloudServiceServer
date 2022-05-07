package com.mal.cloud.auth.domain.useCase

import com.mal.cloud.auth.data.UserService
import com.mal.cloud.auth.domain.entity.UserEntity
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.domain.dto.AuthData
import com.mal.cloud.auth.domain.dto.UserInfo
import org.springframework.stereotype.Component

@Component
class AuthUseCase(
    private val userService: UserService
) {
    fun register(username: String, password: String, userRole: UserRole): AuthData {
        return userService.register(username, password, userRole).toAuthData()
    }

    fun login(username: String, password: String): AuthData {
        return userService.login(username, password).toAuthData()
    }
}