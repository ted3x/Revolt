/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 11:46 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 11:46 PM
 */

package chat.revolt.app.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import chat.revolt.core.NetworkState
import chat.revolt.core.network.NetworkStateManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkStateManagerImpl : NetworkStateManager,
    ConnectivityManager.NetworkCallback() {

    val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val networkStateFlow: MutableStateFlow<NetworkState> =
        MutableStateFlow(NetworkState.Connected)

    override fun initialize(context: Context) {
        val connectivityManager =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(getNetworkRequest(), this)
    }

    override fun getStateFlow(): Flow<NetworkState> {
        return networkStateFlow.asStateFlow()
    }

    override fun getCurrentState(): NetworkState {
        return networkStateFlow.value
    }

    override suspend fun setState(networkState: NetworkState) {
        networkStateFlow.emit(networkState)
    }

    override suspend fun connect() {
        networkStateFlow.emit(NetworkState.Connected)
    }

    override suspend fun disconnect() {
        networkStateFlow.emit(NetworkState.Disconnected)
    }

    override suspend fun noInternet() {
        networkStateFlow.emit(NetworkState.NoInternet)
    }

    override suspend fun isConnected(): Boolean {
        return networkStateFlow.value is NetworkState.Connected
    }

    override suspend fun isDisconnected(): Boolean {
        return networkStateFlow.value is NetworkState.Disconnected
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        scope.launch { setState(NetworkState.Connected) }
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        scope.launch { setState(NetworkState.Disconnected) }
    }

    private fun getNetworkRequest(): NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()
}