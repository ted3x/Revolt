/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:39 PM
 */

package chat.revolt.splash.data.repository

import chat.revolt.core.server_config.RevoltConfig
import chat.revolt.splash.data.data_source.SplashDataSource
import chat.revolt.splash.data.mapper.RevoltConfigMapper
import chat.revolt.splash.domain.repository.SplashRepository

class SplashRepositoryImpl(
    private val dataSource: SplashDataSource,
    private val mapper: RevoltConfigMapper
) :
    SplashRepository {
    override suspend fun getRevoltConfig(): RevoltConfig {
        return mapper.map(dataSource.getRevoltConfig())
    }
}