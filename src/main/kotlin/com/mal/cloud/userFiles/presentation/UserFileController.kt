package com.mal.cloud.userFiles.presentation

import com.mal.cloud.userFiles.domain.useCase.UserFilesUseCase
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserFileController(
    private val userFilesUseCase: UserFilesUseCase
) {
    @GetMapping("/files")
    fun loadFile(pageable: Pageable): ResponseEntity<Any> {
        return userFilesUseCase.getUserFiles(pageable)
    }

    @GetMapping("/files/{fileName}")
    fun loadFile(@PathVariable fileName: String): ResponseEntity<Any> {
        return userFilesUseCase.getUserFile(fileName)
    }
}