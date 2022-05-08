package com.mal.cloud.main.error

data class DefaultError(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String
)