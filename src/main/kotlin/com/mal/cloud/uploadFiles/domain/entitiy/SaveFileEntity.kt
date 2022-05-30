package com.mal.cloud.uploadFiles.domain.entitiy

import com.mal.cloud.uploadFiles.data.database.table.FileInfo
import com.mal.cloud.uploadFiles.domain.dto.StorageFile

data class SaveFileEntity(
    val fileInfo: FileInfo
) {
    fun toStorageFile(): StorageFile {
        return StorageFile(
            fileInfo.fileHash,
            fileInfo.fileType,
            fileInfo.originFileName
        )
    }
}