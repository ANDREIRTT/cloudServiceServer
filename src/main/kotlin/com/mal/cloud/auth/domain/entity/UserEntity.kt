package com.mal.cloud.auth.domain.entity

import com.mal.cloud.auth.data.table.Usr

data class UserEntity(
    val token: String?,
    val userDetails: Usr
) {
}