package com.mal.cloud.auth.domain.useCase

import com.mal.cloud.auth.data.exceptions.JwtTokenException
import com.mal.cloud.auth.domain.repository.AuthFilterRepository
import com.mal.cloud.auth.presentation.configuration.exceptionHandle.AuthResponse
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthFilterUseCase(
    private val authFilterRepository: AuthFilterRepository,
    private val authResponse: AuthResponse
) {
    fun filter(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        try {
            authFilterRepository.filter(request)
        } catch (e: JwtTokenException) {
            authResponse.initResponse(response, e)
        }
    }
}