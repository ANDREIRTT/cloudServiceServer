package com.mal.cloud.userFiles.domain.repository

import com.mal.cloud.userFiles.domain.entity.UserFileEntity
import com.mal.cloud.userFiles.domain.entity.UserFilesEntity
import org.springframework.data.domain.Pageable

interface UserFilesRepository {
    fun getUserFiles(pageable: Pageable): UserFilesEntity

    fun getUserFile(fileHash: String): UserFileEntity
}