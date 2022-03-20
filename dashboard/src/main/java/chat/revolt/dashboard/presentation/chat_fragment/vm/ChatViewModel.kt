/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 2:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 2:53 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.dashboard.domain.repository.MessagesRepository
import chat.revolt.dashboard.presentation.chat_fragment.MessagesManager
import chat.revolt.dashboard.presentation.chat_fragment.adapter.MessageUiModel
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.domain.models.channel.Channel
import chat.revolt.domain.models.member.Member
import chat.revolt.domain.repository.ChannelRepository
import chat.revolt.domain.repository.UserRepository
import chat.revolt.domain.repository.member.MemberRepository
import chat.revolt.socket.server.ServerDataSource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel(
    private val dataSource: ServerDataSource,
    private val manager: MessagesManager,
    private val messagesRepository: MessagesRepository,
    private val channelRepository: ChannelRepository,
    private val userRepository: UserRepository,
    private val memberRepository: MemberRepository
) : BaseViewModel() {

    val typers: MutableLiveData<String?> = MutableLiveData()
    private val typersList: MutableList<String> = mutableListOf()
    private lateinit var messageListener: Job
    private lateinit var channelStartTypingListener: Job
    private lateinit var channelStopTypingListener: Job
    val currentChannel = MutableLiveData<Channel>()
    val flow get() = manager.getMessages().mapLatest { it.map { mapToUiModel(it) } }
    val initialMessages: MutableStateFlow<List<MessageUiModel>> = MutableStateFlow(emptyList())
    val isEndReached = manager.isEndReached
    val initialPhaseFinished = MutableLiveData(false)

    var currentServer: String? = null
    private var loadingJob: Job? = null

    private suspend fun mapToUiModel(message: Message): MessageUiModel {
        val user = memberRepository.getMember(currentServer!!, message.authorId)
            ?: userRepository.getUser(message.authorId)
        return MessageUiModel(
            id = message.id,
            authorId = message.authorId,
            authorName = if(user is Member) user.nickname ?: "" else if(user is User) user.username else "",
            authorAvatarUrl = if(user is Member) user.avatar?.url ?: "" else if(user is User) user.avatarUrl else "",
            content = if(message.content is Message.Content.Message) (message.content as Message.Content.Message).content else "",
            attachments = message.attachments,
            edited = message.edited,
            mentions = message.mentions,
            replies = message.replies
        )
    }

    fun loadMore(isInitial: Boolean = false) {
        loadingJob = viewModelScope.launch {
            manager.loadMore(isInitial)
            if (isInitial)
                initialPhaseFinished.postValue(true)
        }
    }

    fun changeChannel(serverId: String, channelId: String) {
        currentServer = serverId
        loadingJob?.cancel()
        manager.initChannel(channelId)
        viewModelScope.launch { initialMessages.emit(manager.getInitialMessages().map { mapToUiModel(it) }) }
        stopEventListeners()
        typersList.clear()
        typers.value = null
        viewModelScope.launch {
            currentChannel.postValue(channelRepository.getChannel(channelId))
            startEventListeners(channelId)
        }
        loadMore(isInitial = true)
    }

    private fun startEventListeners(channelId: String) {
        messageListener = viewModelScope.launch {
            dataSource.onMessage(channelId = channelId).cancellable().collect {
                messagesRepository.addMessage(it)
            }
        }
        channelStartTypingListener = viewModelScope.launch {
            dataSource.onChannelStartTyping(channelId).cancellable().collect {
                if (it.user.username !in typersList) {
                    typersList.add(it.user.username)
                    typers.postValue(getTypersMessage(typersList))
                }
            }
        }
        channelStopTypingListener = viewModelScope.launch {
            dataSource.onChannelStopTyping(channelId).cancellable().collect {
                if (it.user.username in typersList) {
                    typersList.remove(it.user.username)
                    typers.postValue(getTypersMessage(typersList))
                }
            }
        }
        viewModelScope.launch {
            dataSource.onChannelUpdate().collectLatest {
                println(it)
            }
        }
    }

    private fun stopEventListeners() {
        if (this::messageListener.isInitialized)
            messageListener.cancel()
        if (this::channelStartTypingListener.isInitialized)
            channelStartTypingListener.cancel()
        if (this::channelStopTypingListener.isInitialized)
            channelStopTypingListener.cancel()
    }

    private fun getTypersMessage(typersList: MutableList<String>): String? {
        val typers = typersList.size
        return when {
            typers == 1 -> "${typersList.first()} is typing..."
            typers == 2 -> "${typersList.first()} and ${typersList.get(1)} are typing..."
            typers == 3 -> "${typersList.first()}, ${typersList.get(1)} and ${typersList.get(2)} are typing..."
            typers >= 4 -> "${typersList.first()}, ${typersList.get(1)} and ${typers - 2} others are typing..."
            else -> null
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch { manager.sendMessage(message) }
    }
}