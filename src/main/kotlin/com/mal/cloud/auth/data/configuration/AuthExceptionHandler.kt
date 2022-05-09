package com.mal.cloud.auth.data.configuration

import com.mal.cloud.auth.data.configuration.exception.AuthResponseComponent
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthExceptionHandler(
    private val authResponseComponent: AuthResponseComponent
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        authResponseComponent.initResponse(response, authException)
    }
}