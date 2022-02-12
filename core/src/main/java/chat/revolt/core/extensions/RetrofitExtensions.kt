/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 12:56 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 12:56 AM
 */

package chat.revolt.core.extensions

import kotlinx.coroutines.delay
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

inline fun <T> Response<T>.bodyOrThrow(): T = if (isSuccessful) body()!! else throw HttpException(this)

suspend fun <T> withRetry(
    defaultDelay: Long = 100,
    maxAttempts: Int = 3,
    shouldRetry: (Throwable) -> Boolean = ::defaultShouldRetry,
    block: suspend () -> T
): T {
    repeat(maxAttempts) { attempt ->
        val response = runCatching { block() }

        when {
            response.isSuccess -> return response.getOrThrow()
            response.isFailure -> {
                val exception = response.exceptionOrNull()!!

                // The response failed, so lets see if we should retry again
                if (attempt == maxAttempts - 1 || !shouldRetry(exception)) {
                    throw exception
                }

                val nextDelay = attempt * attempt * defaultDelay
                delay(nextDelay)
            }
        }
    }

    // We should never hit here
    throw IllegalStateException("Unknown exception from executeWithRetry")
}

private fun defaultShouldRetry(throwable: Throwable) = when (throwable) {
    is HttpException -> throwable.code() == 429
    is IOException -> true
    else -> false
}