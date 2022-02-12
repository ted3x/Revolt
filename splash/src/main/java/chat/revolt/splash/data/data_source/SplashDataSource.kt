/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:37 PM
 */

package chat.revolt.splash.data.data_source

import chat.revolt.splash.data.dto.RevoltConfigDto

interface SplashDataSource {

    suspend fun getRevoltConfig(): RevoltConfigDto
}