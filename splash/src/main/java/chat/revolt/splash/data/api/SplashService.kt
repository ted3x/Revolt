/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:34 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:34 PM
 */

package chat.revolt.splash.data.api

import chat.revolt.splash.data.dto.RevoltConfigDto
import retrofit2.Call
import retrofit2.http.GET

interface SplashService {

    @GET("")
    fun getRevoltConfig(): Call<RevoltConfigDto>
}