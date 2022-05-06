package com.mal.cloud.auth.domain.repository

import com.mal.cloud.auth.data.table.User

interface AuthRepository {
    fun login(username: String, password: String)

    fun register(username: String, password: String)

    fun getAllUsers(): List<User>
}