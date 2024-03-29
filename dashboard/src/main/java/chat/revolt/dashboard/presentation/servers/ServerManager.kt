/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 7:04 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 7:04 PM
 */

package chat.revolt.dashboard.presentation.servers

import chat.revolt.core.extensions.ResultWrapper.Companion.onError
import chat.revolt.core.extensions.ResultWrapper.Companion.onSuccess
import chat.revolt.dashboard.R
import chat.revolt.dashboard.domain.models.fetch_members.FetchMembersRequest
import chat.revolt.dashboard.domain.repository.members.MembersRepository
import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ServerRepository
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import java.util.*

class ServerManager(
    private val serversRepository: ServerRepository,
    private val membersRepository: MembersRepository,
    private val userRepository: UserRepository
) {

    private var coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var currentServerJob: Job? = null
    private var categories: List<Server.Category>? = null
    private var selectedChannelId: String? = null

    var currentServerId: String? = null
        private set
    val servers = serversRepository.getServers()
    val serverBadgeRes: MutableStateFlow<Int?> = MutableStateFlow(null)
    val serverBanner: MutableStateFlow<String?> = MutableStateFlow(null)
    val serverName: MutableStateFlow<String?> = MutableStateFlow(null)

    private val serversWithFetchedMembers = mutableMapOf<String, Boolean>()

    private var onServerChangeListeners: MutableMap<Int, ((serverId: String, categories: List<Server.Category>?, selectedChannelId: String) -> Unit)?> =
        mutableMapOf()

    fun initServer(serverId: String) {
        currentServerJob?.cancel()
        currentServerId = serverId
        currentServerJob = coroutineScope.launch {
            serversRepository.getServerAsFlow(currentServerId!!).cancellable()
                .collectLatest { server ->
                    categories = server.categories
                    if(serversWithFetchedMembers[server.id] == null || serversWithFetchedMembers[server.id] == false) {
                        fetchMembers(server.id)
                        serversWithFetchedMembers[server.id] = true
                    }
                    serverName.emit(server.name)
                    serverBanner.emit(server.banner?.url)
                    serverBadgeRes.emit(getServerBadge(server))

                    if (selectedChannelId != server.selectedChannelId || server.selectedChannelId == null) {
                        selectedChannelId = server.selectedChannelId ?: server.channels.first()
                        updateSelectedChannel(selectedChannelId!!)
                    }

                    onServerChangeListeners.forEach {
                        it.value?.invoke(server.id, categories, selectedChannelId!!)
                    }
                }
        }
    }

    private suspend fun fetchMembers(serverId: String) {
        membersRepository.fetchMembers(request = FetchMembersRequest(serverId)).onSuccess {
            membersRepository.addMembers(it.members)
            userRepository.addUsers(it.users)
        }.onError{ _, _ ->
            serversWithFetchedMembers[serverId] = false
        }
    }

    fun setOnServerChangeListener(listener: (serverId: String, categories: List<Server.Category>?, selectedChannelId: String) -> Unit) {
        onServerChangeListeners[onServerChangeListeners.size] = listener
    }

    suspend fun updateSelectedChannel(channelId: String) {
        serversRepository.updateSelectedChannel(currentServerId!!, channelId)
    }

    suspend fun onCategoryVisibilityChange(categoryId: String) {
        categories?.forEach {
            if (it.id == categoryId) {
                it.isVisible = it.isVisible?.not()
                return@forEach
            }
        }
        serversRepository.updateCategory(currentServerId!!, categories!!)
    }

    private fun getServerBadge(server: Server): Int? {
        return when (server.flags) {
            Server.Flags.Official -> R.drawable.ic_revolt_badge
            Server.Flags.Verified -> R.drawable.ic_verified_badge
            else -> null
        }
    }

    fun destroy() {
        for (i in 0..onServerChangeListeners.size) {
            onServerChangeListeners[i] = null
        }
        onServerChangeListeners.clear()
    }
}