package com.mal.cloud.uploadFiles.domain.entitiy

import org.springframework.core.io.Resource
import org.springframework.http.MediaType

data class LoadFileEntity(
    val resource: Resource,
    val mediaType: MediaType
)