package com.mal.cloud.auth.data.configuration.exceptionHandle

import com.fasterxml.jackson.databind.ObjectMapper
import com.mal.cloud.main.error.DefaultError
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletResponse

@Component
class AuthResponse {
    fun initResponse(response: HttpServletResponse, authException: RuntimeException) {
        response.addHeader("Content-Type", "application/json;charset=UTF-8")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(
            ObjectMapper().writeValueAsString(
                DefaultError(
                    timestamp = Date().toInstant().toString(),
                    status = response.status,
                    error = authException.message ?: "null",
                    message = "Ошибка авторизации"
                )
            )
        )
        response.writer.flush()
    }
}