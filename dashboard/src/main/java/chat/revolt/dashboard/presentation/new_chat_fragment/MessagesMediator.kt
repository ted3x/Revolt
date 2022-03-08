/*
 * Created by Tedo Manvelidze(ted3x) on 2/26/22, 10:44 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/26/22, 10:44 PM
 */

package chat.revolt.dashboard.presentation.new_chat_fragment

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.data.local.database.RevoltDatabase
import chat.revolt.data.local.entity.channel.ChannelRemoteKey
import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.data.local.mappers.MessageDBMapper
import chat.revolt.data.local.mappers.UserDBMapper
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class NewMessagesMediator(
    private val userDBMapper: UserDBMapper,
    private val messageDBMapper: MessageDBMapper,
    private val channelId: String,
    private val database: RevoltDatabase,
    private val repository: ChannelRepository
) : RemoteMediator<Int, MessageEntity>() {

    private val messageDao = database.messageDao()
    private val userDao = database.userDao()
    private val remoteKey = database.channelRemoteKeyDao()
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKey.remoteKeyByPost(channelId)
                    }
                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextPageKey
                }
            }
            val response = repository.fetchMessages(
                channelId = channelId,
                request = FetchMessagesRequest(
                    limit = if (loadType == LoadType.REFRESH) state.config.initialLoadSize else state.config.pageSize,
                    before = loadKey
                )
            )
            val messages = response.messages

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    messageDao.clear(channelId)
                    remoteKey.remoteKeyByPost(channelId)
                }
                userDao.addUsers(response.users.map { userDBMapper.mapToEntity(it) })
                remoteKey.insert(ChannelRemoteKey(channelId, if(messages.isEmpty()) null else messages.last().id))
                messageDao.addMessages(messages.map { messageDBMapper.mapToEntity(it) })
            }

            MediatorResult.Success(
                endOfPaginationReached = messages.size < 20
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}