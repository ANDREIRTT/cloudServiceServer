package com.mal.cloud.auth.domain.entity

import com.mal.cloud.auth.data.table.Usr
import com.mal.cloud.auth.domain.dto.AuthData

data class UserEntity(
    val token: String,
    val usr: Usr
) {
    fun toAuthData(): AuthData {
        return AuthData(
            token,
            usr.username,
            usr.userRole.name
        )
    }
}