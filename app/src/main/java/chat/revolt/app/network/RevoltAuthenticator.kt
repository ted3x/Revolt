/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 3:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 3:14 PM
 */

package chat.revolt.app.network

import okhttp3.*
import okhttp3.Route

class RevoltAuthenticator(private val token: String = ""): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
       // val accessToken: String = accountRepository.getAccessToken()
       // return newRequestWithAccessToken(response.request(), updatedAccessToken)
        TODO()
    }

    private fun isRequestWithAccessToken(response: Response): Boolean {
        val header = response.request().header("Authorization")
        return header != null
    }

    private fun newRequestWithAccessToken(
        request: Request,
        accessToken: String
    ): Request? {
        return request.newBuilder()
            .header("Authorization", accessToken)
            .build()
    }
}