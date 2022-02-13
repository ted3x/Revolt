/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:21 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:20 PM
 */

package chat.revolt.socket.di

import chat.revolt.socket.adapter.LoggingMessageAdapter
import chat.revolt.core.server_config.RevoltConfigManager
import chat.revolt.socket.api.RevoltSocket
import chat.revolt.socket.api.RevoltSocketImpl
import chat.revolt.socket.event_resolver.EventResolver
import chat.revolt.socket.server.allEventsStream
import chat.revolt.socket.server.channel.ChannelEvent
import chat.revolt.socket.server.channel.ChannelEventManager
import chat.revolt.socket.server.channel.ChannelEventManagerImpl
import chat.revolt.socket.server.channel.ChannelEventResolver
import chat.revolt.socket.server.channel.type.ChannelEventType
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val revoltSocketModule = module {
    single {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        })
    }
    single(StringQualifier("WSS_URL")) { get<RevoltConfigManager>().getConfig().ws }
    single { get<OkHttpClient>().newWebSocketFactory("wss://ws.revolt.chat") }
    single {
        Scarlet.Builder()
            .webSocketFactory(factory = get())
            .addMessageAdapterFactory(
                LoggingMessageAdapter.Factory(
                    MoshiMessageAdapter.Factory()
                )
            )
            .addStreamAdapterFactory(chat.revolt.socket.adapter.CoroutinesStreamAdapterFactory())
            .build()
    }
    single { get<Scarlet>().create(RevoltSocket::class.java) }
    single { RevoltSocketImpl(instance = get()) }

    single { Moshi.Builder().build() }
    single<EventResolver<ChannelEvent, ChannelEventType>> { ChannelEventResolver(moshi = get()) }
    single {
        get<RevoltSocket>().allEventsStream<ChannelEvent, ChannelEventType>(
            eventResolver = get()
        )
    }
    single<ChannelEventManager> { ChannelEventManagerImpl(channelStreams = get()) }
}
