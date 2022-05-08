package com.mal.cloud.uploadFiles.presentation

import com.mal.cloud.uploadFiles.domain.useCase.StorageUseCase
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/uploads")
class StorageController(
    private val storageUseCase: StorageUseCase
) {

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") multipartFile: MultipartFile): Boolean {
        storageUseCase.storeFile(multipartFile)
        return true
    }

    @GetMapping("/files/{userId}/{fileName}")
    fun loadFile(@PathVariable userId: Long, @PathVariable fileName: String): ResponseEntity<Resource> {
        return storageUseCase.loadFile(userId, fileName)
    }
}