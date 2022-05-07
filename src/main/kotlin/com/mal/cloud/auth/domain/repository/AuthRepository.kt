package com.mal.cloud.auth.domain.repository

import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.domain.entity.UserEntity

interface AuthRepository {
    fun login(username: String, password: String): UserEntity

    fun register(username: String, password: String, userRole: UserRole): UserEntity
}