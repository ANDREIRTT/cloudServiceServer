package com.mal.cloud.auth.presentation.exceptionHandler

data class DefaultError(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String
)