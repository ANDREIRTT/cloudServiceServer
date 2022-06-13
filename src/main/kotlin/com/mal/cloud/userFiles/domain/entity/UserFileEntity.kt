package com.mal.cloud.userFiles.domain.entity

import org.springframework.core.io.Resource
import org.springframework.http.MediaType

data class UserFileEntity(
    val resource: Resource,
    val mediaType: MediaType
)