package com.mal.cloud.uploadFiles.data.database.dbRepository

import com.mal.cloud.uploadFiles.data.database.table.FileInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FileInfoRepository : JpaRepository<FileInfo, Long> {
    fun findFileInfoByFileHash(hash: String): List<FileInfo>
}