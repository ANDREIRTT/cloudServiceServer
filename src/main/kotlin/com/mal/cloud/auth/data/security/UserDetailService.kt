package com.mal.cloud.auth.data.security

import com.mal.cloud.auth.data.dbRepository.UserDbRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailService(
    private val userDbRepository: UserDbRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userDbRepository.findUserByUsername(username)
            ?: throw UsernameNotFoundException("user not found")
    }
}