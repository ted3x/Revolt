/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:28 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:28 AM
 */

package chat.revolt.data.local.entity.account

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey
    val userId: String,
    val token: String,
    val name: String
)
