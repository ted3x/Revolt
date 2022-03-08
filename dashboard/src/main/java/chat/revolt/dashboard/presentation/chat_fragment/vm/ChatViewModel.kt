/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 2:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 2:53 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.domain.models.Message
import chat.revolt.socket.server.ServerDataSource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel(
    private val dataSource: ServerDataSource,
    private val repository: ChannelRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val typers: MutableLiveData<String?> = MutableLiveData()
    private val typersList: MutableList<String> = mutableListOf()
    private lateinit var messageListener: Job
    private lateinit var channelStartTypingListener: Job
    private lateinit var channelStopTypingListener: Job
    val currentChannel = MutableLiveData<String>()
    //var flow: Flow<PagingData<Message>>? =
     //   repository.getMessages("01F7ZSBSFHCAAJQ92ZGTY67HMN").cachedIn(viewModelScope)

    fun changeChannel(channelId: String) {
        viewModelScope.launch {
            stopEventListeners()
            typersList.clear()
            typers.value = null
            startEventListeners()
            //createPager(channelId)
        }
    }

    private fun startEventListeners() {
        messageListener = viewModelScope.launch {
            dataSource.onMessage(channelId = currentChannel.value!!).cancellable().collect {
                //channelRepository.addMessage(it)
            }
        }
        channelStartTypingListener = viewModelScope.launch {
            dataSource.onChannelStartTyping(currentChannel.value!!).cancellable().collect {
                if (it.user.username !in typersList) {
                    typersList.add(it.user.username)
                    typers.postValue(getTypersMessage(typersList))
                }
            }
        }
        channelStopTypingListener = viewModelScope.launch {
            dataSource.onChannelStopTyping(currentChannel.value!!).cancellable().collect {
                if (it.user.username in typersList) {
                    typersList.remove(it.user.username)
                    typers.postValue(getTypersMessage(typersList))
                }
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