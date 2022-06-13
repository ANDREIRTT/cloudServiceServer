package com.mal.cloud.uploadFiles.domain.repository

import com.mal.cloud.uploadFiles.domain.entitiy.FileEntity
import org.springframework.web.multipart.MultipartFile

interface StorageRepository {
    fun storeFile(file: MultipartFile): FileEntity

    fun deleteFile(fileHash: String): Boolean
}