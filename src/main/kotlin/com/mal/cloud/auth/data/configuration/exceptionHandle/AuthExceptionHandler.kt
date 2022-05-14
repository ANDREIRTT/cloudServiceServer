package com.mal.cloud.auth.data.configuration.exceptionHandle

import com.mal.cloud.auth.data.configuration.exceptionHandle.AuthResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthExceptionHandler(
    private val authResponse: AuthResponse
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        authResponse.initResponse(response, authException)
    }
}