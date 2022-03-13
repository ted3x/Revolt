/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 4:21 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 4:21 PM
 */

package chat.revolt.app.network

import android.content.Context
import android.widget.FrameLayout
import chat.revolt.app.RVApp
import chat.revolt.core.BaseActivity
import chat.revolt.core.NetworkErrorHandler
import chat.revolt.core.R
import com.google.android.material.snackbar.Snackbar

class NetworkErrorHandlerImpl(private val context: Context) :
    NetworkErrorHandler {

    private val currentActivity get() = (context as? RVApp)?.currentActivity?.get() as? BaseActivity
    // TODO("Check based on exception type")
    override fun handleException(exception: Throwable) {
        currentActivity?.findViewById<FrameLayout>(R.id.container)?.let { view ->
            Snackbar.make(
                view,
                context.getString(R.string.network_an_error_occured_please_try_again),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}