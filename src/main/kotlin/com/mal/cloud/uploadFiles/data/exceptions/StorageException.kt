package com.mal.cloud.uploadFiles.data.exceptions


class StorageException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)