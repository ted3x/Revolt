/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:26 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 8:42 PM
 */

package chat.revolt.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import chat.revolt.data.local.dao.*
import chat.revolt.data.local.entity.account.AccountEntity
import chat.revolt.data.local.entity.channel.ChannelEntity
import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.data.local.entity.server.ServerEntity
import chat.revolt.data.local.entity.user.UserEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [UserEntity::class, AccountEntity::class, ChannelEntity::class, MessageEntity::class, ServerEntity::class]
)
@TypeConverters(DatabaseConverters::class)
abstract class RevoltDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun channelDao(): ChannelDao
    abstract fun messageDao(): MessageDao
    abstract fun serverDao(): ServerDao
}