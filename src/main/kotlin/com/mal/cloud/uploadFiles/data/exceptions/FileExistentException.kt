package com.mal.cloud.uploadFiles.data.exceptions

class FileExistentException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)