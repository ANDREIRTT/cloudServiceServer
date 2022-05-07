package com.mal.cloud.auth.data.security

import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val BEARER = "Bearer "

@Component
class JWTFilter(
    private val jwtUtil: JWTUtil,
    private val userDetailService: UserDetailService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.isNotBlank() && authHeader.startsWith(BEARER)) {
            val jwt = authHeader.substring(BEARER.length)
            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header")
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
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token")
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}