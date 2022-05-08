package com.mal.cloud.uploadFiles.domain.dto

import com.mal.cloud.uploadFiles.data.database.table.FileType

data class StorageFile(
    val fileHash: String,
    val fileType: FileType,
    val originFileName: String,
)