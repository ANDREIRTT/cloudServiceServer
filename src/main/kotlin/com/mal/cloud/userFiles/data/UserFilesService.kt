package com.mal.cloud.userFiles.data

import com.mal.cloud.auth.data.component.AuthenticatedUserComponent
import com.mal.cloud.uploadFiles.data.database.dbRepository.FileInfoRepository
import com.mal.cloud.userFiles.domain.entity.UserFilesEntity
import com.mal.cloud.userFiles.domain.repository.UserFilesRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserFilesService(
    private val fileInfoRepository: FileInfoRepository,
    private val authenticatedUserComponent: AuthenticatedUserComponent
) : UserFilesRepository {

    override fun getUserFiles(pageable: Pageable): UserFilesEntity {
        val user = authenticatedUserComponent.getAuthenticatedUser()

        val files =
            fileInfoRepository.findAllUserFiles(user.userId!!, PageRequest.of(pageable.pageNumber, pageable.pageSize))
        return UserFilesEntity(
            files.pageable.pageNumber,
            files.pageable.pageSize,
            files.isLast,
            files.content
        )
    }
}