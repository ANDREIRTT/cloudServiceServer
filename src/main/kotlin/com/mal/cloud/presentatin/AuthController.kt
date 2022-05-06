package com.mal.cloud.presentatin

import com.mal.cloud.data.table.User
import com.mal.cloud.domain.useCase.AuthUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AuthController(
    private val authUseCase: AuthUseCase
) {
    @GetMapping("/reg")
    fun register(): Boolean {
        authUseCase.register("auys", "djf")
        return true
    }

    @GetMapping("/list")
    fun getAll(): List<User> {
        return authUseCase.getAllUsers()
    }
}