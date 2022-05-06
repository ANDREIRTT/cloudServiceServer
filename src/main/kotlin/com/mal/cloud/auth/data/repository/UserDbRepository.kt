package com.mal.cloud.auth.data.repository

import com.mal.cloud.auth.data.table.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDbRepository : CrudRepository<User, Long> {
    fun findUserByUsername(username: String): User?
}