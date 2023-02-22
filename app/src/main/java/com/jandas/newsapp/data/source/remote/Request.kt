package com.jandas.newsapp.data.source.remote

import com.jandas.newsapp.data.Result
import retrofit2.Response

/**
 * A generic class that holds a value or error.
 * @param <T>
 */
suspend fun <T> request(call: suspend () -> Response<T>): Result<T> {
    return try {
        val response = call()
        when (response.isSuccessful) {
            true -> Result.Success(response.body())
            false -> {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.Error(Throwable("$response $errorBody"), response.code(), response.message(), errorBody)
            }
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}
