/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 12:56 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 12:56 AM
 */

package chat.revolt.core.extensions

import android.util.Log
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Response<T>.bodyOrThrow(): T =
    if (isSuccessful) body()!! else throw HttpException(this).also {
        Log.e(
            "Revolt-Ex",
            this.message().toString()
        )
    }

suspend fun <T> Call<T>.awaitResult(): T {
    val response = this.awaitResponse()
    return response.bodyOrThrow()
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Failure(val code: Int? = null, val error: Error) : ResultWrapper<Nothing>()

    companion object {
        suspend fun <T> ResultWrapper<T>.onSuccess(block: suspend (T) -> Unit): ResultWrapper<T> {
            if (this is Success) block.invoke(this.data)
            return this
        }

        suspend fun <T> ResultWrapper<T>.onError(block: suspend (Int?, Error) -> Unit): ResultWrapper<T> {
            if (this is Failure) block.invoke(this.code, this.error)
            return this
        }
    }
}

sealed class Error {
    @JsonClass(generateAdapter = true)
    data class GenericError(val throwable: HttpException) : Error()
    object NetworkError : Error()
    object Timeout : Error()
    object NoConnection : Error()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is SocketTimeoutException -> {
                    ResultWrapper.Failure(error = Error.Timeout)
                }
                is UnknownHostException -> ResultWrapper.Failure(error = Error.NoConnection)
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.Failure(code, errorResponse!!)
                }
                else -> {
                    ResultWrapper.Failure(error = Error.NetworkError)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): Error.GenericError? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(Error.GenericError::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}