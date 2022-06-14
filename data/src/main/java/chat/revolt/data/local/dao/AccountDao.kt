/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:28 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:28 AM
 */

package chat.revolt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import chat.revolt.data.local.entity.account.AccountEntity

@Dao
interface AccountDao {

    @Insert
    suspend fun insertAccount(accountEntity: AccountEntity)

    @Query("SELECT userId from `account` LIMIT 1")
    suspend fun getUserId(): String?

    @Query("SELECT `token` FROM `account` LIMIT 1")
    suspend fun getToken(): String?

    @Query("DELETE FROM account")
    suspend fun drop()
}