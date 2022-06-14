/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:56 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:56 AM
 */

package chat.revolt.domain.repository

import chat.revolt.domain.models.Account

interface AccountRepository {

    suspend fun addAccount(account: Account)
    suspend fun getUserId(): String?
    suspend fun getToken(): String?
}