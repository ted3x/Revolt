/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 8:57 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 8:57 PM
 */

package chat.revolt.core.server_config

interface RevoltConfigManager {

    fun setConfig(config: RevoltConfig)

    fun getConfig(): RevoltConfig

    fun getConfigFeatures(): RevoltConfig.Features
}