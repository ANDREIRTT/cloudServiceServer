package com.mal.cloud.auth.data

import com.auth0.jwt.exceptions.JWTVerificationException
import com.mal.cloud.auth.data.exceptions.JwtTokenException
import com.mal.cloud.auth.data.security.JWTUtil
import com.mal.cloud.auth.data.security.UserDetailService
import com.mal.cloud.auth.domain.repository.AuthFilterRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

private const val BEARER = "Bearer "

private const val HEADER = "Authorization"

@Service
class AuthFilterService(
    private val userDetailService: UserDetailService,
    private val jwtUtil: JWTUtil,
) : AuthFilterRepository {

    override fun filter(request: HttpServletRequest) {
        val authHeader = request.getHeader(HEADER)

        if (authHeader != null && authHeader.isNotBlank() && authHeader.startsWith(BEARER)) {
            val jwt = authHeader.substring(BEARER.length)
            if (jwt.isBlank()) {
                throw JwtTokenException("Invalid JWT Token in Bearer Header")
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
                    throw JwtTokenException("Invalid JWT Token")
                }
            }
        }
    }
}