package com.mal.cloud.auth.data

import com.mal.cloud.auth.data.entity.UserEntity
import com.mal.cloud.auth.data.exceptions.UserAlreadyExistException
import com.mal.cloud.auth.data.exceptions.UserInvalidPasswordException
import com.mal.cloud.auth.data.repository.UserDbRepository
import com.mal.cloud.auth.data.table.User
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.domain.repository.AuthRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDbRepository: UserDbRepository,
    private val userBeanService: UserBeanService
) : AuthRepository, UserDetailsService {

    override fun login(username: String, password: String): UserEntity {
        val userDetails = loadUserByUsername(username)

        if (userDetails.password == userBeanService.getPasswordEncoder().encode(password)) {
            return UserEntity(userDetails)
        } else {
            throw UserInvalidPasswordException("password not valid")
        }
    }

    override fun register(username: String, password: String, userRole: UserRole): UserEntity {
        val userDetails = userDbRepository.findUserByUsername(username)
        if (userDetails == null) {
            return UserEntity(
                userDbRepository.save(
                    User(
                        username,
                        userBeanService.getPasswordEncoder().encode(password),
                        userRole
                    )
                )
            )
        } else {
            throw UserAlreadyExistException("user exist")
        }
    }

    override fun getAllUsers(): List<UserEntity> {
        return userDbRepository.findAll().toList().map {
            UserEntity(it)
        }
    }

//    override fun login(username: String, password: String) {
//        var userDetails = loadUserByUsername(username)
//
//    }
//
//    override fun register(username: String, password: String, userRole: UserRole) {
//        userDbRepository.save(User(username, password, userRole))
//    }
//
//    override fun getAllUsers(): List<User> {
//        return userDbRepository.findAll().toList()
//    }

    override fun loadUserByUsername(username: String): UserDetails {
        return userDbRepository.findUserByUsername(username)
            ?: throw UsernameNotFoundException("user not found")
    }
}