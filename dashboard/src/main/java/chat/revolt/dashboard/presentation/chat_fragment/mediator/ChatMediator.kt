/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:32 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:32 AM
 */

package chat.revolt.dashboard.presentation.chat_fragment.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.data.local.dao.ChannelDao
import chat.revolt.data.local.dao.MessageDao
import chat.revolt.data.local.database.RevoltDatabase
import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.data.local.mappers.MessageDBMapper
import chat.revolt.domain.models.ChatItem
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class ChatMediator(
    private val revoltDatabase: RevoltDatabase,
    private val messageDao: MessageDao,
    private val repository: ChannelRepository,
    private val mapper: MessageDBMapper
) :
    RemoteMediator<Int, MessageEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.firstItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.

                    lastItem.id
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.

            val request = if (loadKey != null) {
                FetchMessagesRequest(
                    before = loadKey
                )
            } else
                FetchMessagesRequest()
            val response = repository.fetchMessages(
                channelId = "01FVSDSHJ6QSH0DZJYEBTZ2FES",
                request = request
            )
            revoltDatabase.withTransaction {

                val newMessages = response.map { mapper.mapToEntity(it) }
                messageDao.addMessages(newMessages)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.size < 20
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}