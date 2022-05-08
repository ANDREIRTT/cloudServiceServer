package com.mal.cloud.auth.domain.useCase

import com.mal.cloud.auth.data.AuthService
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.domain.dto.AuthData
import org.springframework.stereotype.Component

@Component
class AuthUseCase(
    private val authService: AuthService
) {
    fun register(username: String, password: String, userRole: UserRole): AuthData {
        return authService.register(username, password, userRole).toAuthData()
    }

    fun login(username: String, password: String): AuthData {
        return authService.login(username, password).toAuthData()
    }
}