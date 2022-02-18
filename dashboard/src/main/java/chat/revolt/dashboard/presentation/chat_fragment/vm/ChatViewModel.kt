/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 2:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 2:53 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.dashboard.presentation.chat_fragment.PagingData
import chat.revolt.dashboard.presentation.chat_fragment.PagingManager
import chat.revolt.domain.models.Message
import chat.revolt.socket.server.ServerDataSource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChatViewModel(
    private val pagingManager: PagingManager,
    private val channelRepository: ChannelRepository,
    private val dataSource: ServerDataSource
) : BaseViewModel() {

    var pagingData: PagingData? = null
    var isLoading: Boolean = true
    val typers: MutableLiveData<String?> = MutableLiveData()
    val typersList: MutableList<String> = mutableListOf()
    private lateinit var messageListener: Job
    private lateinit var channelStartTypingListener: Job
    private lateinit var channelStopTypingListener: Job
    val currentChannel = MutableLiveData<String>()
    val flow: Flow<List<Message>> get() = channelRepository.getMessages(channelId = currentChannel.value!!)

    fun changeChannel(channelId: String) {
        currentChannel.value = channelId
        pagingData = null
        isLoading = true
        stopEventListeners()
        startEventListeners()
    }

    private fun startEventListeners() {
        messageListener = viewModelScope.launch {
            dataSource.onMessage(channelId = currentChannel.value!!).cancellable().collect {
                channelRepository.addMessage(it)
            }
        }
        channelStartTypingListener = viewModelScope.launch {
            dataSource.onChannelStartTyping(currentChannel.value!!).cancellable().collect {
                typersList.add(it.user.username)
                typers.postValue(getTypersMessage(typersList))
            }
        }
        channelStopTypingListener = viewModelScope.launch {
            dataSource.onChannelStopTyping(currentChannel.value!!).cancellable().collect {
                typersList.remove(it.user.username)
                typers.postValue(getTypersMessage(typersList))
            }
        }
    }

    private fun stopEventListeners() {
        if (this::messageListener.isInitialized)
            messageListener.cancel()
        if (this::messageListener.isInitialized)
            messageListener.cancel()
        if (this::channelStopTypingListener.isInitialized)
            channelStopTypingListener.cancel()
    }

    fun load() {
        if (pagingData?.isPaginationEndReached == true) return
        isLoading = true
        viewModelScope.launch {
            pagingData =
                pagingManager.load(channelId = currentChannel.value!!, pagingData?.lastId)
        }
    }

    fun getTypersMessage(typersList: MutableList<String>): String? {
        val typers = typersList.size
        return when {
            typers == 1 -> "${typersList.first()} is typing..."
            typers == 2 -> "${typersList.first()} and ${typersList.get(1)} are typing..."
            typers == 3 -> "${typersList.first()}, ${typersList.get(1)} and ${typersList.get(2)} are typing..."
            typers >= 4 -> "${typersList.first()}, ${typersList.get(1)} and ${typers - 2} others are typing..."
            else -> null
        }
    }
}