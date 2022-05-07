package com.mal.cloud.auth.domain.dto

data class AuthData(
    val token: String,
    val username: String,
    val userRole: String
)