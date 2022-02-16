/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 2:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 2:53 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.vm

import androidx.lifecycle.viewModelScope
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.dashboard.presentation.chat_fragment.PagingData
import chat.revolt.dashboard.presentation.chat_fragment.PagingManager
import chat.revolt.data.local.dao.MessageDao
import chat.revolt.data.local.mappers.MessageDBMapper
import chat.revolt.domain.models.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val pagingManager: PagingManager,
    private val channelRepository: ChannelRepository
) : BaseViewModel() {

    var pagingData: PagingData? = null
    var isLoading: Boolean = true
    var flow: Flow<List<Message>> = channelRepository.getMessages(channelId = "")

    init {
        load()
    }

    fun load() {
        if (pagingData?.isPaginationEndReached == true) return
        isLoading = true
        viewModelScope.launch {
            pagingData = pagingManager.load(channelId = "01F7ZSBSFHCAAJQ92ZGTY67HMN", pagingData?.lastId)
        }
    }
}