/*
 * Created by Tedo Manvelidze(ted3x) on 6/15/22, 1:10 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 6/15/22, 1:10 AM
 */

package chat.revolt.domain.models

data class Account(
    val userId: String,
    val token: String,
    val name: String
)
