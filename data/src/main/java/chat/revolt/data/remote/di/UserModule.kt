/*
 * Created by Tedo Manvelidze(ted3x) on 2/16/22, 10:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/16/22, 10:00 PM
 */

package chat.revolt.data.remote.di

import chat.revolt.data.local.mappers.UserDBMapper
import chat.revolt.data.remote.data_source.UserDataSource
import chat.revolt.data.remote.data_source.UserDataSourceImpl
import chat.revolt.data.remote.mappers.user.*
import chat.revolt.data.remote.service.UserService
import chat.revolt.data.repository.UserRepositoryImpl
import chat.revolt.domain.interactors.AddUserInDbUseCase
import chat.revolt.domain.interactors.GetUserUseCase
import chat.revolt.domain.repository.UserRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val userModule = module {
    single { RelationshipDtoToRelationshipMapper(relationshipStatusMapper = get()) }
    single { StatusDtoToStatusMapper() }
    single { RelationshipStatusMapper() }
    single { BotDtoToBotMapper() }
    single {
        UserDtoToUserMapper(
            relationshipStatusMapper = get(),
            statusMapper = get(),
            relationsMapper = get(),
            botMapper = get()
        )
    }
    single { UserDBMapper() }

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

    single { GetUserUseCase(repository = get()) }
    single { AddUserInDbUseCase(repository = get()) }
}