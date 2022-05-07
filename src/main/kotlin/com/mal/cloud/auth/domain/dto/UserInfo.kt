package com.mal.cloud.auth.domain.dto

data class UserInfo(
    val userId:Long,
    val username: String,
    val userRole: String,
)