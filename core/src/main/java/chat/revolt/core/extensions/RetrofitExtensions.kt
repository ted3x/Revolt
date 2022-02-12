/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 12:56 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 12:56 AM
 */

package chat.revolt.core.extensions

import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse

fun <T> Response<T>.bodyOrThrow(): T =
    if (isSuccessful) body()!! else throw HttpException(this)

suspend fun <T> Call<T>.awaitResult(): T {
    val response = this.awaitResponse()
    return response.bodyOrThrow()
}