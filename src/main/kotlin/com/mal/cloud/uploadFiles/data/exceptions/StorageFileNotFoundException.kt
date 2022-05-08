package com.mal.cloud.uploadFiles.data.exceptions

class StorageFileNotFoundException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)