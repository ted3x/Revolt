/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:28 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:28 AM
 */

package chat.revolt.data.local.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AccountDao {

    @Query("SELECT `token` FROM `account` LIMIT 1")
    suspend fun getToken(): String?

    @Query("DELETE FROM account")
    suspend fun drop()
}