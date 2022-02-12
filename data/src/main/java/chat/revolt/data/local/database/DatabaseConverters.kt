/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 2:08 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 2:08 AM
 */

package chat.revolt.data.local.database

import androidx.room.TypeConverter
import chat.revolt.data.local.entity.user.UserEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.IllegalStateException

class DatabaseConverters {
    private val moshi = Moshi.Builder().build()

    private val relationshipType =
        Types.newParameterizedType(List::class.java,UserEntity.Relationship::class.java)
    private val relationshipAdapter = moshi.adapter<List<UserEntity.Relationship>>(relationshipType)

    private val statusType =
        Types.newParameterizedType(UserEntity.Status::class.java)
    private val statusAdapter = moshi.adapter<UserEntity.Status>(statusType)

    private val botType =
        Types.newParameterizedType(UserEntity.Bot::class.java)
    private val botAdapter = moshi.adapter<UserEntity.Bot>(botType)

    @TypeConverter
    fun stringToRelationships(string: String): List<UserEntity.Relationship> {
        return relationshipAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun relationshipsToString(members: List<UserEntity.Relationship>): String {
        return relationshipAdapter.toJson(members)
    }

    @TypeConverter
    fun stringToStatus(string: String): UserEntity.Status {
        return statusAdapter.fromJson(string) ?: throw IllegalStateException("Wrong $string as UserEntity.Status")
    }

    @TypeConverter
    fun statusToString(status: UserEntity.Status): String {
        return statusAdapter.toJson(status)
    }

    @TypeConverter
    fun stringToBot(string: String): UserEntity.Bot? {
        return botAdapter.fromJson(string)
    }

    @TypeConverter
    fun botToString(bot: UserEntity.Bot): String {
        return botAdapter.toJson(bot)
    }
}