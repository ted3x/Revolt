/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:38 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:38 PM
 */

package chat.revolt.splash.domain.repository

import chat.revolt.core.server_config.RevoltConfig

interface SplashRepository {

    suspend fun getRevoltConfig(): RevoltConfig
}