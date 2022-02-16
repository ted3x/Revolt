/*
 * Created by Tedo Manvelidze(ted3x) on 2/16/22, 10:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/16/22, 10:00 PM
 */

package chat.revolt.data.remote.di

import chat.revolt.data.local.mappers.UserDBMapper
import chat.revolt.data.remote.mappers.user.*
import org.koin.dsl.module

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
}