package com.mal.cloud.auth.data.entity

import org.springframework.security.core.userdetails.UserDetails

data class UserEntity(
    val userDetails: UserDetails
) {
}