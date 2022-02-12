/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 3:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 3:14 PM
 */

package chat.revolt.app.network

import chat.revolt.domain.repository.AccountRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class RevoltAuthenticator(
    private val accountRepository: AccountRepository,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        val accessToken = runBlocking { accountRepository.getToken() }
        return if(accessToken != null) newRequestWithAccessToken(response.request, accessToken) else response.request
    }

    private fun newRequestWithAccessToken(
        request: Request,
        accessToken: String
    ): Request {
        return request.newBuilder()
            .header("x-access-token", accessToken)
            .build()
    }
}