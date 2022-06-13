package com.mal.cloud.uploadFiles.presentation

import com.mal.cloud.uploadFiles.domain.useCase.StorageUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/uploads")
class StorageController(
    private val storageUseCase: StorageUseCase
) {

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") multipartFile: MultipartFile): ResponseEntity<Any> {
        return storageUseCase.storeFile(multipartFile)
    }

    @DeleteMapping("/delete/{fileName}")
    fun deleteFile(@PathVariable fileName: String): ResponseEntity<Any> {
        return storageUseCase.deleteFile(fileName)
    }
}