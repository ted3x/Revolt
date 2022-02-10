/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 6:10 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 6:10 PM
 */

package chat.revolt.data.repository

import chat.revolt.data.remote.data_source.UserDataSource
import chat.revolt.data.local.dao.UserDao
import chat.revolt.data.local.mappers.UserEntityToUserMapper
import chat.revolt.data.remote.mappers.user.UserDtoToUserMapper
import chat.revolt.domain.models.User
import chat.revolt.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userDataSource: UserDataSource,
    private val userEntityMapper: UserEntityToUserMapper,
    private val userDtoMapper: UserDtoToUserMapper
) : UserRepository {
    override suspend fun getUser(userId: String): User? {
        return userDao.getUser(userId)?.let { userEntityMapper.map(it) }
    }

    override suspend fun fetchUser(userId: String): User? {
        return userDataSource.getUser(userId)?.let { userDtoMapper.map(it) }
    }
}