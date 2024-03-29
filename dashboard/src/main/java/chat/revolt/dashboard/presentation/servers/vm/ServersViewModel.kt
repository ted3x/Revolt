/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:51 PM
 */

package chat.revolt.dashboard.presentation.servers.vm

import androidx.lifecycle.viewModelScope
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.dashboard.presentation.servers.ChannelManager
import chat.revolt.dashboard.presentation.servers.ServerManager
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ServersViewModel(
    userRepository: UserRepository,
    channelManager: ChannelManager,
    private val serverManager: ServerManager,
) : BaseViewModel() {

    val serverBadgeRes = serverManager.serverBadgeRes
    val serverBanner = serverManager.serverBanner
    val serverName = serverManager.serverName
    val servers = serverManager.servers
    val channels = channelManager.channels
    val currentUserImageUrl: MutableStateFlow<String?> = MutableStateFlow(null)
    val onServerChange: MutableStateFlow<String?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            userRepository.getCurrentUserAsFlow().collectLatest {
                currentUserImageUrl.emit(it.avatarUrl)
            }
        }
        serverManager.setOnServerChangeListener { _, _, selectedChannelId ->
            viewModelScope.launch { onServerChange.emit(selectedChannelId) }
        }
    }

    fun changeServer(serverId: String) {
        if (serverManager.currentServerId == serverId) return
        serverManager.initServer(serverId)
    }

    fun onCategoryVisibilityChange(categoryId: String) {
        viewModelScope.launch { serverManager.onCategoryVisibilityChange(categoryId) }
    }

    fun onChannelClick(channelId: String) {
        viewModelScope.launch { serverManager.updateSelectedChannel(channelId) }
    }

    fun getCurrentServerId() = serverManager.currentServerId

    override fun onCleared() {
        super.onCleared()
        serverManager.destroy()
    }

}