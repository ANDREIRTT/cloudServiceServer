package com.mal.cloud.userFiles.exceptions

class StorageFileNotFoundException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)