package com.mal.cloud.uploadFiles.domain.repository

import com.mal.cloud.uploadFiles.domain.entitiy.LoadFileEntity
import com.mal.cloud.uploadFiles.domain.entitiy.SaveFileEntity
import org.springframework.web.multipart.MultipartFile

interface StorageRepository {
    fun storeFile(file: MultipartFile): SaveFileEntity

    fun loadFile(fileHash: String): LoadFileEntity
}