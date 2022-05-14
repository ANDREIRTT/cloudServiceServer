package com.mal.cloud.auth.data.security

import com.auth0.jwt.exceptions.JWTVerificationException
import com.mal.cloud.auth.data.configuration.exceptionHandle.AuthResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val BEARER = "Bearer "

private const val HEADER = "Authorization"

@Component
class JWTFilter(
    private val jwtUtil: JWTUtil,
    private val userDetailService: UserDetailService,
    private val authResponse: AuthResponse
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HEADER)

        if (authHeader != null && authHeader.isNotBlank() && authHeader.startsWith(BEARER)) {
            val jwt = authHeader.substring(BEARER.length)
            if (jwt.isBlank()) {
                authResponse.initResponse(response, RuntimeException("Invalid JWT Token in Bearer Header"))
                return
            } else {
                try {
                    val email: String = jwtUtil.validateTokenAndRetrieveSubject(jwt)
                    val userDetails: UserDetails = userDetailService.loadUserByUsername(email)

                    val authToken =
                        UsernamePasswordAuthenticationToken(email, userDetails.password, userDetails.authorities)

                    if (SecurityContextHolder.getContext().authentication == null) {
                        SecurityContextHolder.getContext().authentication = authToken
                    }
                } catch (exc: JWTVerificationException) {
                    authResponse.initResponse(response, RuntimeException("Invalid JWT Token"))
                    return
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}