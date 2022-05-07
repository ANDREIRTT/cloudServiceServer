package com.mal.cloud.auth.domain.useCase

import com.mal.cloud.auth.data.UserService
import com.mal.cloud.auth.data.entity.UserEntity
import com.mal.cloud.auth.data.table.UserRole
import org.springframework.stereotype.Component

@Component
class AuthUseCase(
    private val userService: UserService
) {
    fun register(username: String, password: String, userRole: UserRole): UserEntity {
        return userService.register(username, password, userRole)
    }

//    fun getAllUsers(): List<User> {
//        return userService.getAllUsers()
//    }

    fun getAllUsers(): List<UserEntity> {
        return userService.getAllUsers()
    }

    fun login(username: String, password: String): UserEntity {
        return userService.login(username, password)
    }

    fun createAdmin(username: String, password: String): UserEntity {
        return userService.register(username, password, UserRole.ROLE_ADMIN)
    }
}