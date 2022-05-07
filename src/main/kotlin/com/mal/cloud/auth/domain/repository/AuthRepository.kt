package com.mal.cloud.auth.domain.repository

import com.mal.cloud.auth.data.entity.UserEntity
import com.mal.cloud.auth.data.table.UserRole

interface AuthRepository {
    fun login(username: String, password: String): UserEntity

    fun register(username: String, password: String, userRole: UserRole): UserEntity

    fun getAllUsers(): List<UserEntity>
}