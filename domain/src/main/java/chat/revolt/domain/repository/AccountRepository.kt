/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:56 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:56 AM
 */

package chat.revolt.domain.repository

interface AccountRepository {

    suspend fun getToken(): String?
}