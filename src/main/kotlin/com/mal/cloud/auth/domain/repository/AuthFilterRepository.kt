package com.mal.cloud.auth.domain.repository

import javax.servlet.http.HttpServletRequest

interface AuthFilterRepository {
    fun filter(
        request: HttpServletRequest
    )
}