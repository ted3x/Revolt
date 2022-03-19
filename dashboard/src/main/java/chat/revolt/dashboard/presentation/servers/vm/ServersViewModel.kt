/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:51 PM
 */

package chat.revolt.dashboard.presentation.servers.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ChannelRepository
import chat.revolt.domain.repository.ServerRepository
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.launch

class ServersViewModel(
    private val userRepository: UserRepository,
    private val serversRepository: ServerRepository,
    private val channelRepository: ChannelRepository
) : BaseViewModel() {

    val currentServer = MutableLiveData<Server>()
    val serverChannels get() = channelRepository.getChannels(currentServer.value!!.id)
    val currentUser = userRepository.getCurrentUserAsFlow()
    val servers = serversRepository.getServers()

    fun changeServer(serverId: String) {
        if(currentServer.value?.id == serverId) return
        viewModelScope.launch { currentServer.value = serversRepository.getServer(serverId) }
    }
}