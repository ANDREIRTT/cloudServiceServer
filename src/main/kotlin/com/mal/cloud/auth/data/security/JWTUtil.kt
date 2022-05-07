package com.mal.cloud.auth.data.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

private const val SUBJECT = "User Details"

private const val claim = "email"

private const val ISSUER = "cloud/service/mal"

@Component
class JWTUtil {
    @Value("\${jwt-secret}")
    private lateinit var secret: String

    fun generateToken(email: String): String {
        return JWT.create()
            .withSubject(SUBJECT)
            .withClaim(claim, email)
            .withIssuedAt(Date())
            .withIssuer(ISSUER)
            .sign(Algorithm.HMAC256(secret))
    }

    fun validateTokenAndRetrieveSubject(token: String): String {
        val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(secret))
            .withSubject(SUBJECT)
            .withIssuer(ISSUER)
            .build()
        val jwt: DecodedJWT = verifier.verify(token)
        return jwt.getClaim(claim).asString()
    }
}