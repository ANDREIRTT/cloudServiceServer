package com.mal.cloud.uploadFiles.data.database.dbRepository

import com.mal.cloud.uploadFiles.data.database.table.FileInfo
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FileInfoRepository : JpaRepository<FileInfo, Long> {
    fun findFileInfoByFileHash(hash: String): List<FileInfo>

    @Query("SELECT * FROM FILE_INFO LEFT JOIN USR on FILE_INFO.USER_ID = :userId", nativeQuery = true)
    fun findAllUserFiles( @Param("userId") userId: Long, pageable: Pageable): Slice<FileInfo>
}