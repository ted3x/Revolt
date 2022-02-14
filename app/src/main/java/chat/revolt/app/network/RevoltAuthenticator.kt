/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 3:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 3:14 PM
 */

package chat.revolt.app.network

import chat.revolt.domain.repository.AccountRepository
import kotlinx.coroutines.runBlocking
import okhttp3.*

class RevoltInterceptor(
    private val accountRepository: AccountRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = "qUL5nTUE0PR6XOI7V2URjhA8A25y0FVmMYfmdeNwFH8OZG4W6ftJJAiYAAAS8PR1" ?: runBlocking { accountRepository.getToken()  }
        val newRequest = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("x-session-token", accessToken!!)
            .build();
        return chain.proceed(newRequest);
    }
}