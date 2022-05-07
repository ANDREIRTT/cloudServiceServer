package com.mal.cloud.auth.domain.entity

import com.mal.cloud.auth.data.table.Usr
import com.mal.cloud.auth.domain.dto.AuthData
import com.mal.cloud.auth.domain.dto.UserInfo

data class UserEntity(
    val token: String?,
    val usr: Usr
) {
    fun toAuthData(): AuthData {
        return AuthData(
            token ?: "null",
            usr.username,
            usr.userRole.name
        )
    }

    fun toUserInfo(): UserInfo {
        return UserInfo(
            usr.userId!!,
            usr.username,
            usr.userRole.name
        )
    }
}