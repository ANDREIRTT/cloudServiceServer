package com.mal.cloud.auth.data.component

import com.mal.cloud.auth.data.dbRepository.UserDbRepository
import com.mal.cloud.auth.data.table.Usr
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import javax.naming.AuthenticationException

@Component
class AuthenticatedUserComponent(
    private val userDbRepository: UserDbRepository
) {
    fun getAuthenticatedUser(): Usr {
        return when (val security = SecurityContextHolder.getContext().authentication.principal) {
            is String -> {
                userDbRepository.findUserByUsername(security)!!
            }
            is Usr -> {
                security
            }
            else -> {
                throw AuthenticationException()
            }
        }
    }
}