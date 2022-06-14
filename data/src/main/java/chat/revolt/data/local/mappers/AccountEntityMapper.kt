/*
 * Created by Tedo Manvelidze(ted3x) on 6/15/22, 1:16 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 6/15/22, 1:16 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.data.local.entity.account.AccountEntity
import chat.revolt.domain.models.Account

class AccountEntityMapper {
    fun mapToDomain(from: AccountEntity): Account {
        return Account(userId = from.userId, token = from.token, name = from.name)
    }

    fun mapToEntity(from: Account): AccountEntity {
        return AccountEntity(userId = from.userId, token = from.token, name = from.name)
    }
}
