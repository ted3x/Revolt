/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 2:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 2:50 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.di

import chat.revolt.dashboard.data.ChannelService
import chat.revolt.dashboard.data.data_source.ChannelDataSource
import chat.revolt.dashboard.data.data_source.ChannelDataSourceImpl
import chat.revolt.dashboard.data.mapper.FetchMessageMapper
import chat.revolt.dashboard.data.mapper.SendMessageMapper
import chat.revolt.dashboard.data.repository.MessagesRepositoryImpl
import chat.revolt.dashboard.domain.repository.MessagesRepository
import chat.revolt.dashboard.presentation.chat_fragment.MessagesManager
import chat.revolt.dashboard.presentation.chat_fragment.ui.ChatFragment
import chat.revolt.dashboard.presentation.chat_fragment.vm.ChatViewModel
import chat.revolt.data.local.mappers.MessageDBMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val chatModule = module {
    scope<ChatFragment> {
        scoped<ChannelService> { get<Retrofit>().create(ChannelService::class.java) }
        scoped<ChannelDataSource> { ChannelDataSourceImpl(service = get()) }
        scoped { FetchMessageMapper(userMapper = get(), messageMapper = get(), memberMapper = get()) }
        scoped { MessageDBMapper(userDBMapper = get(), userRepository = get(), attachmentEntityMapper = get()) }
        scoped {
            SendMessageMapper(
                messageMapper = get(),
                attachmentMapper = get(),
                masqueradeMapper = get()
            )
        }
        scoped<MessagesRepository> {
            MessagesRepositoryImpl(
                messageDao = get(),
                dataSource = get(),
                mapper = get(),
                messageMapper = get(),
                sendMessageMapper = get()
            )
        }
        scoped {
            MessagesManager(
                messagesRepository = get(),
                userRepository = get(),
                database = get(),
                networkStateManager = get(),
                memberRepository = get()
            )
        }
        viewModel {
            ChatViewModel(
                dataSource = get(),
                manager = get(),
                messagesRepository = get(),
                channelRepository = get(),
                userRepository = get(),
                memberRepository = get()
            )
        }
    }
}