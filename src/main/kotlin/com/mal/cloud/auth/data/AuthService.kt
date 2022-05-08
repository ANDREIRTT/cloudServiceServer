package com.mal.cloud.auth.data

import com.mal.cloud.auth.data.dbRepository.UserDbRepository
import com.mal.cloud.auth.data.exceptions.UserAlreadyExistException
import com.mal.cloud.auth.data.exceptions.UserInvalidValuesException
import com.mal.cloud.auth.data.security.JWTUtil
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.data.table.Usr
import com.mal.cloud.auth.domain.entity.UserEntity
import com.mal.cloud.auth.domain.repository.AuthRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userDbRepository: UserDbRepository,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JWTUtil
) : AuthRepository {

    override fun login(username: String, password: String): UserEntity {
        val userTable = userDbRepository.findUserByUsername(username) ?: throw UserInvalidValuesException()

        val authInputToken = UsernamePasswordAuthenticationToken(username, password)

        try {
            authenticationManager.authenticate(authInputToken)
        } catch (auth: AuthenticationException) {
            throw UserInvalidValuesException()
        }
        return UserEntity(jwtUtil.generateToken(username), userTable)
    }

    override fun register(username: String, password: String, userRole: UserRole): UserEntity {
        val userDetails = userDbRepository.findUserByUsername(username)

        if (userDetails == null) {
            return UserEntity(
                jwtUtil.generateToken(username),
                userDbRepository.save(
                    Usr(
                        username,
                        passwordEncoder.encode(password),
                        userRole
                    )
                )
            )
        } else {
            throw UserAlreadyExistException("user exist")
        }
    }
}