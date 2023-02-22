package com.jandas.newsapp.data


/**
 * A generic class that holds a value or error.
 * @param <T>
 */
sealed class Result<in T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(
        val exception: Throwable,
        val code: Int? = null,
        val message: String? = null,
        val errorBody: String? = null
    ) : Result<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
