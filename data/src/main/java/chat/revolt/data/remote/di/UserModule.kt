/*
 * Created by Tedo Manvelidze(ted3x) on 2/16/22, 10:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/16/22, 10:00 PM
 */

package chat.revolt.data.remote.di

import chat.revolt.data.local.mappers.AvatarEntityMapper
import chat.revolt.data.local.mappers.MetadataEntityMapper
import chat.revolt.data.local.mappers.UserDBMapper
import chat.revolt.data.remote.data_source.UserDataSource
import chat.revolt.data.remote.data_source.UserDataSourceImpl
import chat.revolt.data.remote.mappers.MetadataMapper
import chat.revolt.data.remote.mappers.channel.ChannelMapper
import chat.revolt.data.remote.mappers.message.AttachmentMapper
import chat.revolt.data.remote.mappers.message.MasqueradeMapper
import chat.revolt.data.remote.mappers.message.MessageContentMapper
import chat.revolt.data.remote.mappers.message.MessageMapper
import chat.revolt.data.remote.mappers.user.*
import chat.revolt.data.remote.service.UserService
import chat.revolt.data.repository.ServerRepositoryImpl
import chat.revolt.data.repository.UserRepositoryImpl
import chat.revolt.domain.interactors.AddUserInDbUseCase
import chat.revolt.domain.interactors.GetUserUseCase
import chat.revolt.domain.repository.ServerRepository
import chat.revolt.domain.repository.UserRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val userModule = module {
    single { RelationshipMapper(relationshipStatusMapper = get()) }
    single { StatusMapper() }
    single { RelationshipStatusMapper() }
    single { BotMapper() }
    single { AvatarMapper(metadataMapper = get()) }
    single { MetadataMapper() }
    single {
        UserMapper(
            relationshipStatusMapper = get(),
            statusMapper = get(),
            relationsMapper = get(),
            botMapper = get(),
            avatarMapper = get()
        )
    }
    single { MetadataEntityMapper() }
    single { AvatarEntityMapper(metadataMapper = get()) }
    single { UserDBMapper(avatarMapper = get()) }

    single {
        MessageMapper(
            userRepository = get(),
            messageContentMapper = get(),
            attachmentMapper = get(),
            masqueradeMapper = get()
        )
    }
    single { MessageContentMapper(userRepository = get()) }
    single { AttachmentMapper(metadataMapper = get()) }
    single { MasqueradeMapper() }

    single {
        ChannelMapper(
            userRepository = get(),
            attachmentMapper = get(),
            serverRepository = get()
        )
    }

    single<UserService> { get<Retrofit>().create(UserService::class.java) }
    single<UserDataSource> { UserDataSourceImpl(service = get()) }
    single<UserRepository> {
        UserRepositoryImpl(
            userDao = get(),
            userDataSource = get(),
            userEntityMapper = get(),
            userDtoMapper = get()
        )
    }
    single<ServerRepository> { ServerRepositoryImpl() }

    single { GetUserUseCase(repository = get()) }
    single { AddUserInDbUseCase(repository = get()) }
}