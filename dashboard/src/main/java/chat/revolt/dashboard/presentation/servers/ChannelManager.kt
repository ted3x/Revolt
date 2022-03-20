/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 7:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 7:14 PM
 */

package chat.revolt.dashboard.presentation.servers

import chat.revolt.dashboard.presentation.servers.adapter.ChannelUiItem
import chat.revolt.domain.models.channel.Channel
import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ChannelRepository
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest

class ChannelManager(
    serverManager: ServerManager,
    private val userRepository: UserRepository,
    private val channelRepository: ChannelRepository
) {

    private var coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var channelJob: Job? = null

    val channels: MutableStateFlow<List<ChannelUiItem>> = MutableStateFlow(listOf())

    init {
        serverManager.setOnServerChangeListener { serverId, categories, selectedChannelId ->
            channelJob?.cancel()
            channelJob = coroutineScope.launch {
                channelRepository.getChannels(serverId).cancellable().collectLatest {
                    channels.emit(mapToUiItems(it, categories, selectedChannelId))
                }
            }
        }
    }

    private suspend fun mapToUiItems(
        channels: List<Channel>,
        categories: List<Server.Category>?,
        selectedChannelId: String
    ): List<ChannelUiItem> {
        val channelsWithingCategories = categories?.flatMap { it.channels } ?: emptyList()
        val nonCategoryChannels =
            channels.filter { it.id !in channelsWithingCategories }
                .map { mapChannelToUi(it, null, selectedChannelId) }
        val list = mutableListOf<ChannelUiItem>()
        categories?.forEach { category ->
            list.add(ChannelUiItem.Category(id = category.id, name = category.title))
            category.channels.forEach {
                val channel = channelRepository.getChannel(it)
                list.add(mapChannelToUi(channel, category, selectedChannelId))
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
                isVisible = category?.isVisible ?: true || selectedChannelId == channel.id
            )
            is Channel.VoiceChannel -> ChannelUiItem.Channel(
                id = channel.id,
                name = channel.name,
                description = channel.description,
                categoryId = category?.id,
                iconUrl = channel.icon?.url,
                isSelected = selectedChannelId == channel.id,
                isVisible = category?.isVisible ?: true || selectedChannelId == channel.id
            )
        }
    }
}