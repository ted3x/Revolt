/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 10:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 10:50 PM
 */

package chat.revolt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chat.revolt.data.local.entity.member.MemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMembers(members: List<MemberEntity>)

    @Query("SELECT * FROM members WHERE serverId LIKE :serverId")
    fun getMembers(serverId: String): Flow<List<MemberEntity>>

    @Query("SELECT * FROM members WHERE serverId LIKE :serverId AND userId LIKE :userId LIMIT 1")
    suspend fun getMember(serverId: String, userId: String): MemberEntity?
}
