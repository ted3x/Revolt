/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 4:36 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 4:36 PM
 */

package chat.revolt.core

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException

@RequiresApi(Build.VERSION_CODES.M)
abstract class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }




}

sealed class NetworkState {
    object Connected: NetworkState()
    object Disconnected: NetworkState()
    object NoInternet: NetworkState()
}