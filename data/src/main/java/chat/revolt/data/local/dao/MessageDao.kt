/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 1:03 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 1:03 AM
 */

package chat.revolt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chat.revolt.data.local.entity.message.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessages(messages: List<MessageEntity>)

    @Query("SELECT * FROM messages WHERE channel LIKE :channelId ORDER BY createdAt")
    fun getMessages(channelId: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE channel LIKE :channelId AND :before < createdAt ORDER BY createdAt")
    fun getMessagesAfter(channelId: String, before: Long): Flow<List<MessageEntity>>

    @Query("DELETE FROM messages WHERE channel LIKE :channelId")
    suspend fun clear(channelId: String)
}