/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 4:21 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 4:21 PM
 */

package chat.revolt.app.network

import android.content.Context
import chat.revolt.app.MainActivity
import chat.revolt.app.RVApp
import chat.revolt.core.NetworkErrorHandler
import chat.revolt.core.R
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

class NetworkErrorHandlerImpl(private val context: Context) :
    NetworkErrorHandler {

    // TODO("Check based on exception type")
    override fun handleException(exception: Throwable) {
        val view = (context as? RVApp)?.currentActivity?.get()?.window?.decorView?.rootView
        view?.let {
            Snackbar.make(
                it,
                context.getString(R.string.network_an_error_occured_please_try_again),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}