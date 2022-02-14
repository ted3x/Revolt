/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 2:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 2:53 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.dashboard.presentation.chat_fragment.mediator.ChatMediator
import chat.revolt.data.local.dao.MessageDao
import chat.revolt.data.local.database.RevoltDatabase
import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.data.local.mappers.MessageDBMapper
import kotlinx.coroutines.flow.*
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.get

@OptIn(ExperimentalPagingApi::class)
class ChatViewModel(
    private val messageDao: MessageDao,
    private val channelRepository: ChannelRepository
) : BaseViewModel() {

    val flow: Flow<PagingData<MessageEntity>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = true, initialLoadSize = 20),
        remoteMediator = ChatMediator(
            revoltDatabase = get(RevoltDatabase::class.java),
            messageDao = messageDao,
            repository = channelRepository,
            mapper = MessageDBMapper()
        )
    ) {
        messageDao.pagingSource()
    }.flow
        .cachedIn(viewModelScope)
}