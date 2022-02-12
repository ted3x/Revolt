/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:28 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:28 PM
 */

package chat.revolt.app.revolt_config_manager

import chat.revolt.core.server_config.RevoltConfig
import chat.revolt.core.server_config.RevoltConfigManager

class RevoltConfigManagerImpl: RevoltConfigManager {

    private lateinit var config: RevoltConfig

    override fun setConfig(config: RevoltConfig) {
        this.config = config
    }

    override fun getConfig() = config

    override fun getConfigFeatures() = config.features
}