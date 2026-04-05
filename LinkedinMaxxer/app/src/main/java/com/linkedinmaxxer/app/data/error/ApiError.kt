package com.linkedinmaxxer.app.data.error

data class ApiError(
    val message: String,
    val code: Int
)

sealed class NetworkException : Exception() {
    data class HttpException(val code: Int, override val message: String) : NetworkException()
    data class NetworkError(override val message: String) : NetworkException()
    data class UnknownError(override val message: String) : NetworkException()
}
