package com.mal.cloud.userFiles.domain.entity

import com.mal.cloud.uploadFiles.data.database.table.FileInfo
import com.mal.cloud.uploadFiles.domain.entitiy.SaveFileEntity
import com.mal.cloud.userFiles.domain.dto.UserFile

data class UserFilesEntity(
    val page: Int,
    val size: Int,
    val isLastPage: Boolean,
    val userFiles: List<FileInfo>
) {
    fun toUserFile(): UserFile {
        return UserFile(
            page,
            size,
            isLastPage,
            userFiles.map {
                SaveFileEntity(it).toStorageFile()
            }
        )
    }
}