package com.mal.cloud.data

import com.mal.cloud.data.repository.UserDbRepository
import com.mal.cloud.data.table.User
import com.mal.cloud.domain.repository.AuthRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDbRepository: UserDbRepository
) : AuthRepository {
    override fun login(username: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun register(username: String, password: String) {
        userDbRepository.save(User(username, password))
    }

    override fun getAllUsers(): List<User> {
        return userDbRepository.findAll().toList()
    }
}