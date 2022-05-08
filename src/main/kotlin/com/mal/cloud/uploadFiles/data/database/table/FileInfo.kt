package com.mal.cloud.uploadFiles.data.database.table

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class FileInfo(
    val originFileName: String,
    val fileHash: String,
    val userId: Long,
    val fileType: FileType,
    @Id
    @GeneratedValue
    val fileId: Long? = null
)