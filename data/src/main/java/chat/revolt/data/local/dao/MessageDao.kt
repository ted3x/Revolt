/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 1:03 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 1:03 AM
 */

package chat.revolt.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chat.revolt.data.local.entity.message.MessageEntity

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessages(messages: List<MessageEntity>)

    @Query("SELECT * FROM messages ORDER BY createdAt")
    fun pagingSource(): PagingSource<Int, MessageEntity>

    @Query("DELETE FROM messages WHERE id = :id")
    suspend fun deleteById(id: String)
}