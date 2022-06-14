/*
 * Created by Tedo Manvelidze(ted3x) on 2/16/22, 10:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/16/22, 10:00 PM
 */

package chat.revolt.data.remote.di

import chat.revolt.data.local.mappers.AttachmentEntityMapper
import chat.revolt.data.local.mappers.AvatarEntityMapper
import chat.revolt.data.local.mappers.MetadataEntityMapper
import chat.revolt.data.local.mappers.UserDBMapper
import chat.revolt.data.local.mappers.channel.ChannelEntityMapper
import chat.revolt.data.local.mappers.server.*
import chat.revolt.data.remote.data_source.UserDataSource
import chat.revolt.data.remote.data_source.UserDataSourceImpl
import chat.revolt.data.remote.data_source.channel.ChannelDataSource
import chat.revolt.data.remote.data_source.channel.ChannelDataSourceImpl
import chat.revolt.data.remote.dto.server.ServerCategoryMapper
import chat.revolt.data.remote.dto.server.RolePermissionsMapper
import chat.revolt.data.remote.dto.server.ServerRolesMapper
import chat.revolt.data.remote.dto.server.SystemMessagesMapper
import chat.revolt.data.remote.mappers.MetadataMapper
import chat.revolt.data.remote.mappers.channel.ChannelMapper
import chat.revolt.data.remote.mappers.message.AttachmentMapper
import chat.revolt.data.remote.mappers.message.MasqueradeMapper
import chat.revolt.data.remote.mappers.message.MessageContentMapper
import chat.revolt.data.remote.mappers.message.MessageMapper
import chat.revolt.data.remote.mappers.server.ServerFlagsMapper
import chat.revolt.data.remote.mappers.server.ServerMapper
import chat.revolt.data.remote.mappers.user.*
import chat.revolt.data.remote.service.UserService
import chat.revolt.data.remote.service.channel.ChannelService
import chat.revolt.data.repository.ChannelRepositoryImpl
import chat.revolt.data.repository.ServerRepositoryImpl
import chat.revolt.data.repository.UserRepositoryImpl
import chat.revolt.domain.interactors.AddUserInDbUseCase
import chat.revolt.domain.interactors.GetUserUseCase
import chat.revolt.domain.repository.ChannelRepository
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
            attachmentMapper = get(),
            userRepository = get(),
            permissionsMapper = get()
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

    single { SystemMessagesMapper() }
    single { ServerCategoryMapper() }
    single { ServerRolesMapper(permissionsMapper = get()) }
    single { RolePermissionsMapper() }
    single { ServerFlagsMapper() }
    single {
        ServerMapper(
            serverCategoryMapper = get(),
            attachmentMapper = get(),
            systemMessagesMapper = get(),
            serverRolesMapper = get(),
            serverFlagsMapper = get()
        )
    }

    single {
        ServerEntityMapper(
            categoriesMapper = get(),
            systemMessagesMapper = get(),
            serverRolesMapper = get(),
            attachmentEntityMapper = get(),
            serverFlagsMapper = get()
        )
    }
    single { ServerCategoryEntityMapper() }
    single { SystemMessagesEntityMapper() }
    single { ServerRolesEntityMapper(permissionsMapper = get()) }
    single { RolePermissionsEntityMapper() }
    single { ServerFlagsEntityMapper() }
    single<ServerRepository> {
        ServerRepositoryImpl(
            serverDao = get(),
            serverEntityMapper = get(),
            serverCategoryMapper = get()
        )
    }

    single { ChannelEntityMapper(attachmentMapper = get(), permissionsMapper = get()) }
    single { AttachmentEntityMapper(metadataMapper = get()) }
    single<ChannelService> { get<Retrofit>().create(ChannelService::class.java) }
    single<ChannelDataSource> { ChannelDataSourceImpl(service = get()) }
    single<ChannelRepository> {
        ChannelRepositoryImpl(
            channelDao = get(),
            channelDataSource = get(),
            channelMapper = get(),
            channelEntityMapper = get()
        )
    }

    single { GetUserUseCase(repository = get()) }
    single { AddUserInDbUseCase(repository = get()) }


}