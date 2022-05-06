package com.mal.cloud.domain.repository

import com.mal.cloud.data.table.User

interface AuthRepository {
    fun login(username: String, password: String)

    fun register(username: String, password: String)

    fun getAllUsers(): List<User>
}