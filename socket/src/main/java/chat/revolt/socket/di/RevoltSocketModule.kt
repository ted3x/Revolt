/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:21 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:20 PM
 */

package chat.revolt.socket.di

import androidx.lifecycle.LifecycleOwner
import chat.revolt.core.server_config.RevoltConfigManager
import chat.revolt.socket.SocketAPI
import chat.revolt.socket.adapter.CoroutinesStreamAdapterFactory
import chat.revolt.socket.adapter.MoshiMessageAdapter
import chat.revolt.socket.api.ClientSocketManager
import chat.revolt.socket.data.channel.mapper.ChannelActionMapper
import chat.revolt.socket.data.ready.mapper.ReadyEventMapper
import chat.revolt.socket.server.ServerDataSource
import chat.revolt.socket.server.ServerDataSourceImpl
import chat.revolt.socket.server.authenticate.AuthenticateDataSource
import chat.revolt.socket.server.authenticate.AuthenticateDataSourceImpl
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
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
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
            .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
            .build()
    }
    single { get<Scarlet>().create(SocketAPI::class.java) }
    single { ClientSocketManager(socket = get()) }
    single { ChannelActionMapper(userRepository = get()) }
    single<ServerDataSource> {
        ServerDataSourceImpl(
            socket = get(),
            userRepository = get(),
            messageMapper = get(),
            channelActionMapper = get(),
            channelMapper = get()
        )
    }
    single { ReadyEventMapper(userMapper = get(), channelMapper = get(), serverMapper = get()) }
    single<AuthenticateDataSource> {
        AuthenticateDataSourceImpl(
            socket = get(),
            readyEventMapper = get()
        )
    }
}
