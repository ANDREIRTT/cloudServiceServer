package com.mal.cloud.uploadFiles.domain.useCase

import com.mal.cloud.uploadFiles.data.StorageService
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class StorageUseCase(
    private val storageService: StorageService
) {
    fun storeFile(file: MultipartFile) {
        storageService.storeFile(file)
    }

    fun loadFile(userId: Long, fileName: String): ResponseEntity<Resource> {
        val resource = storageService.loadFile(userId, fileName)

        return ResponseEntity.ok()
            .header("content-disposition", "inline; filename=\"" + resource.resource.filename + "\"")
            .contentLength(resource.resource.contentLength())
            .contentType(resource.mediaType)
            .body(resource.resource);
    }
}