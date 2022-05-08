package com.mal.cloud.uploadFiles.domain.repository

import com.mal.cloud.uploadFiles.domain.entitiy.StorageEntity
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface StorageRepository {
    fun storeFile(file: MultipartFile)

    fun loadFile(userId: Long, filename: String): StorageEntity
}