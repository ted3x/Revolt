/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 6:57 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 6:57 PM
 */

package chat.revolt.core.loading_manager

interface LoadingManager {

    fun showLoading()

    fun hideLoading()

    fun toggleLoading(toggle: Boolean)
}