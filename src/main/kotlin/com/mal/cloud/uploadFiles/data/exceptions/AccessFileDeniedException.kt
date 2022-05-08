package com.mal.cloud.uploadFiles.data.exceptions

class AccessFileDeniedException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)