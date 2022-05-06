package com.mal.cloud.auth.domain.useCase

import com.mal.cloud.auth.data.UserService
import com.mal.cloud.auth.data.table.User
import org.springframework.stereotype.Component

@Component
class AuthUseCase(
    private val userService: UserService
) {
    fun register(username: String, password: String) {
        userService.register(username, password)
    }

    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }
}