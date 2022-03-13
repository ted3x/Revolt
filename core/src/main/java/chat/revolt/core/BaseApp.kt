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

    val networkState: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    override fun onCreate() {
        super.onCreate()
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(getNetworkRequest(), networkCallback)
    }

    private fun getNetworkRequest(): NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            GlobalScope.launch { networkState.emit(isOnline()) }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            GlobalScope.launch { networkState.emit(false) }
        }
    }

    private fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val mExitValue = mIpAddrProcess.waitFor()
            return mExitValue == 0
        } catch (ignore: InterruptedException) {
            ignore.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }
}