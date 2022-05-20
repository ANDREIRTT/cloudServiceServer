package com.mal.cloud.auth.presentation

import com.mal.cloud.auth.domain.useCase.AuthFilterUseCase
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JWTFilter(
    private val authFilterUseCase: AuthFilterUseCase
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        authFilterUseCase.filter(request, response)

        if (!response.isCommitted) {
            filterChain.doFilter(request, response)
        }
    }
}