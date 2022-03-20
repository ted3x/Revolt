/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:51 PM
 */

package chat.revolt.dashboard.presentation.servers.vm

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.dashboard.R
import chat.revolt.dashboard.presentation.servers.adapter.ChannelUiItem
import chat.revolt.domain.models.channel.Channel
import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ChannelRepository
import chat.revolt.domain.repository.ServerRepository
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ServersViewModel(
    private val userRepository: UserRepository,
    private val serversRepository: ServerRepository,
    private val channelRepository: ChannelRepository
) : BaseViewModel() {

    private var currentServerId: String? = null
    private var categories: List<Server.Category>? = null
    val currentServer get() = serversRepository.getServerAsFlow(currentServerId!!)
    val onServerChange: MutableLiveData<Unit> = MutableLiveData()
    val channels: MutableStateFlow<List<ChannelUiItem>> = MutableStateFlow(emptyList())
    val currentUser = userRepository.getCurrentUserAsFlow()
    val servers = serversRepository.getServers()

    val serverBadgeRes: MutableStateFlow<Int?> = MutableStateFlow(null)
    val serverBanner: MutableStateFlow<String?> = MutableStateFlow(null)
    val serverName: MutableStateFlow<String?> = MutableStateFlow(null)

    val currentUserImageUrl: MutableStateFlow<String?> = MutableStateFlow(null)
    private var currentServerJob: Job? = null
    private var selectedChannelId: String? = null

    init {
        onServerChange.observeForever {
            currentServerJob = viewModelScope.launch {
                currentServer.cancellable().collectLatest { server ->
                    categories = server.categories

                    serverName.emit(server.name)
                    serverBanner.emit(server.banner?.url)
                    serverBadgeRes.emit(getServerBadge(server))

                    if (selectedChannelId != server.selectedChannelId || server.selectedChannelId == null)
                        selectedChannelId = server.selectedChannelId ?: server.channels.first()
                            .also { updateSelectedChannel(currentServerId!!, selectedChannelId!!) }
                    channelRepository.getChannels(server.id).cancellable().collectLatest {
                        channels.emit(mapToUiItems(it, categories))
                    }
                }
            }
        }

        viewModelScope.launch {
            currentUser.collectLatest {
                currentUserImageUrl.emit(it.avatarUrl)
            }
        }
    }

    private suspend fun updateSelectedChannel(serverId: String, channelId: String) {
        serversRepository.updateSelectedChannel(serverId, channelId)
    }

    fun changeServer(serverId: String) {
        if (currentServerId == serverId) return
        currentServerJob?.cancel()
        currentServerId = serverId
        onServerChange.postValue(Unit)
    }

    private suspend fun mapToUiItems(
        channels: List<Channel>,
        categories: List<Server.Category>?
    ): List<ChannelUiItem> {
        val channelsWithingCategories = categories?.flatMap { it.channels } ?: emptyList()
        val nonCategoryChannels =
            channels.filter { it.id !in channelsWithingCategories }
                .map { mapChannelToUi(it, null, selectedChannelId!!) }
        val list = mutableListOf<ChannelUiItem>()
        categories?.forEach { category ->
            list.add(ChannelUiItem.Category(id = category.id, name = category.title))
            category.channels.forEach {
                val channel = channelRepository.getChannel(it)
                list.add(mapChannelToUi(channel, category, selectedChannelId!!))
            }
        }
        return nonCategoryChannels + list
    }

    private suspend fun mapChannelToUi(
        channel: Channel,
        category: Server.Category?,
        selectedChannelId: String,
    ): ChannelUiItem {
        return when (channel) {
            is Channel.DirectMessage -> {
                val user = userRepository.getUser(channel.recipients.last())
                ChannelUiItem.Channel(
                    id = channel.id,
                    name = user.username,
                    description = null,
                    categoryId = category?.id,
                    iconUrl = user.avatarUrl,
                    isSelected = selectedChannelId == channel.id,
                    isVisible = true
                )
            }
            is Channel.Group -> ChannelUiItem.Channel(
                id = channel.id,
                name = channel.name,
                description = channel.description,
                categoryId = category?.id,
                iconUrl = channel.icon?.url,
                isSelected = selectedChannelId == channel.id,
                isVisible = true
            )
            is Channel.SavedMessages -> ChannelUiItem.Channel(
                id = channel.id,
                name = "Saved Notes",
                description = null,
                categoryId = category?.id,
                iconUrl = null,
                isSelected = selectedChannelId == channel.id,
                isVisible = true
            )
            is Channel.TextChannel -> ChannelUiItem.Channel(
                id = channel.id,
                name = channel.name,
                description = channel.description,
                categoryId = category?.id,
                iconUrl = channel.icon?.url,
                isSelected = selectedChannelId == channel.id,
                isVisible = category?.isVisible ?: true
            )
            is Channel.VoiceChannel -> ChannelUiItem.Channel(
                id = channel.id,
                name = channel.name,
                description = channel.description,
                categoryId = category?.id,
                iconUrl = channel.icon?.url,
                isSelected = selectedChannelId == channel.id,
                isVisible = category?.isVisible ?: true
            )
        }
    }

    fun onCategoryVisibilityChange(categoryId: String, isVisible: Boolean) {
        viewModelScope.launch {
            categories?.forEach {
                if (it.id == categoryId) {
                    it.isVisible = isVisible
                    return@forEach
                }
            }
            serversRepository.updateCategory(currentServerId!!, categories!!)
        }
    }

    fun onChannelClick(channelId: String) {
        viewModelScope.launch { updateSelectedChannel(currentServerId!!, channelId) }
    }

    private fun getServerBadge(server: Server): Int? {
        return when (server.flags) {
            Server.Flags.Official -> R.drawable.ic_revolt_badge
            Server.Flags.Verified -> R.drawable.ic_verified_badge
            else -> null
        }
    }
}