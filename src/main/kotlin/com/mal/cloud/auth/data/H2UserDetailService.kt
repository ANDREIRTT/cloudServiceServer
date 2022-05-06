package com.mal.cloud.auth.data

import com.mal.cloud.auth.data.repository.UserDbRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class H2UserDetailService(
    private var userDbRepository: UserDbRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userDbRepository.findUserByUsername(username)
            ?: throw UsernameNotFoundException("user not found")
    }
}