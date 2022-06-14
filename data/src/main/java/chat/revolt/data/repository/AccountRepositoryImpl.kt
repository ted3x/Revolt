/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:55 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:55 AM
 */

package chat.revolt.data.repository

import chat.revolt.data.local.dao.AccountDao
import chat.revolt.data.local.mappers.AccountEntityMapper
import chat.revolt.domain.models.Account
import chat.revolt.domain.repository.AccountRepository

class AccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val accountMapper: AccountEntityMapper
) : AccountRepository {
    override suspend fun addAccount(account: Account) {
        accountDao.insertAccount(accountMapper.mapToEntity(account))
    }

    override suspend fun getUserId(): String? {
        return accountDao.getUserId()
    }

    override suspend fun getToken(): String? {
        return accountDao.getToken()
    }
}