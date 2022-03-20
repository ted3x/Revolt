/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 7:04 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 7:04 PM
 */

package chat.revolt.dashboard.presentation.servers

import chat.revolt.dashboard.R
import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ServerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest

class ServerManager(private val serversRepository: ServerRepository) {

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

    var onServerChange: ((serverId: String, categories: List<Server.Category>?, selectedChannelId: String) -> Unit)? =
        null

    fun initServer(serverId: String) {
        currentServerJob?.cancel()
        currentServerId = serverId
        currentServerJob = coroutineScope.launch {
            serversRepository.getServerAsFlow(currentServerId!!).cancellable()
                .collectLatest { server ->
                    categories = server.categories

                    serverName.emit(server.name)
                    serverBanner.emit(server.banner?.url)
                    serverBadgeRes.emit(getServerBadge(server))

                    if (selectedChannelId != server.selectedChannelId || server.selectedChannelId == null)
                        selectedChannelId = server.selectedChannelId ?: server.channels.first()
                            .also { updateSelectedChannel(selectedChannelId!!) }

                    onServerChange?.invoke(server.id, categories, selectedChannelId!!)
                }
        }
    }

    fun setOnServerChangeListener(listener: (serverId: String, categories: List<Server.Category>?, selectedChannelId: String) -> Unit) {
        this.onServerChange = listener
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
}