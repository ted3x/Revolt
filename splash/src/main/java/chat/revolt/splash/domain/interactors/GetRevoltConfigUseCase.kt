/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:46 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:46 PM
 */

package chat.revolt.splash.domain.interactors

import chat.revolt.core.server_config.RevoltConfig
import chat.revolt.domain.interactors.BaseUseCase
import chat.revolt.splash.domain.repository.SplashRepository

class GetRevoltConfigUseCase(private val repository: SplashRepository): BaseUseCase<Unit, RevoltConfig>() {
    override suspend fun invoke(params: Unit): RevoltConfig {
        return repository.getRevoltConfig()
    }
}