/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 11:44 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 11:44 PM
 */

package chat.revolt.core.network

import android.content.Context
import chat.revolt.core.NetworkState
import kotlinx.coroutines.flow.Flow

interface NetworkStateManager {
    fun initialize(context: Context)
    fun getStateFlow(): Flow<NetworkState>
    fun getCurrentState(): NetworkState
    suspend fun setState(networkState: NetworkState)
    suspend fun connect()
    suspend fun disconnect()
    suspend fun noInternet()

    suspend fun isConnected(): Boolean
    suspend fun isDisconnected(): Boolean
}