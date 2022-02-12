/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 6:58 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 6:58 PM
 */

package chat.revolt.app.loading_manager

import android.content.Context
import chat.revolt.app.RVApp
import chat.revolt.core.BaseActivity
import chat.revolt.core.loading_manager.LoadingManager

class LoadingManagerImpl(private val context: Context): LoadingManager {

    private val currentActivity get() = (context as? RVApp)?.currentActivity?.get() as? BaseActivity

    override fun showLoading() {
        currentActivity?.showLoading()
    }

    override fun hideLoading() {
        currentActivity?.hideLoading()
    }

    override fun toggleLoading(toggle: Boolean) {
        if(toggle) showLoading()
        else hideLoading()
    }

}