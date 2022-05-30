package com.mal.cloud.userFiles.domain.dto

import com.mal.cloud.uploadFiles.domain.dto.StorageFile

data class UserFile(
    val page: Int,
    val size: Int,
    val isLastPage: Boolean,
    val storageFile: List<StorageFile>
)