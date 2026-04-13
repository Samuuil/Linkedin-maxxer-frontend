package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.data.error.NetworkException
import com.linkedinmaxxer.app.domain.model.response.ApiErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.Response

fun <T> requestBody(request: Response<T>): Result<T> {
    return try {
        if (request.isSuccessful) {
            request.body()?.let { Result.success(it) }
                ?: Result.failure(NetworkException.UnknownError("Empty response body"))
        } else {
            val errorBody = request.errorBody()?.string().orEmpty()
            val apiError = Json { ignoreUnknownKeys = true }
                .decodeFromString<ApiErrorResponse>(errorBody)
            Result.failure(
                NetworkException.HttpException(
                    code = request.code(),
                    message = apiError.message ?: "Request failed",
                ),
            )
        }
    } catch (exception: Exception) {
        Result.failure(NetworkException.NetworkError(exception.message ?: "Network error"))
    }
}

fun <T> resultBody(result: Result<T>): Result<T> = result.fold(
    onSuccess = { Result.success(it) },
    onFailure = { Result.failure(it) },
)

suspend fun <T> safeRequest(call: suspend () -> Response<T>): Result<T> = try {
    requestBody(call())
} catch (exception: Exception) {
    Result.failure(NetworkException.NetworkError(exception.message ?: "Network error"))
}
