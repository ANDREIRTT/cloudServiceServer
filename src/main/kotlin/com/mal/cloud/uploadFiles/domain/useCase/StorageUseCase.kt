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
        val storageEntity = storageService.loadFile(userId, fileName)

        return ResponseEntity.ok()
            .header("content-disposition", "inline; filename=\"" + storageEntity.resource.filename + "\"")
            .contentLength(storageEntity.resource.contentLength())
            .contentType(storageEntity.mediaType)
            .body(storageEntity.resource)
    }
}