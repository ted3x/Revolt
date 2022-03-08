/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 6:10 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 6:10 PM
 */

package chat.revolt.data.repository

import chat.revolt.data.remote.data_source.UserDataSource
import chat.revolt.data.local.dao.UserDao
import chat.revolt.data.local.mappers.UserDBMapper
import chat.revolt.data.remote.mappers.user.UserDtoToUserMapper
import chat.revolt.domain.models.User
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userDataSource: UserDataSource,
    private val userEntityMapper: UserDBMapper,
    private val userDtoMapper: UserDtoToUserMapper
) : UserRepository {
    override suspend fun getUser(userId: String): User {
        return userDao.getUser(userId)?.let {
            userEntityMapper.mapToDomain(it)
        } ?: fetchUser(userId) ?: throw IllegalStateException("User with $userId not found")
    }

    // Make Worker
    private suspend fun fetchUser(userId: String): User? {
        return userDataSource.getUser(userId)?.let { userDto ->
            userDtoMapper.map(userDto).also {
                val userEntity = userEntityMapper.mapToEntity(it)
                userDao.addUser(userEntity)
            }
        }
    }

    override suspend fun getCurrentUser(): User? {
        return userDao.getCurrentUser()?.let { userEntityMapper.mapToDomain(it) }
    }

    override suspend fun getMessageAuthor(authorId: String, users: List<User>): User {
        return users.firstOrNull { it.id == authorId } ?: fetchUser(authorId) ?: getCurrentUser() ?: throw IllegalStateException(
            "Current user is null"
        )
    }

    override suspend fun getUsers(userIds: List<String>): List<User> {
        return userIds.toSet().map { getUser(it) }
    }

    override suspend fun addUser(user: User) {
        val userEntity = userEntityMapper.mapToEntity(user)
        userDao.addUser(userEntity)
    }

    override suspend fun addUsers(users: List<User>) {
        userDao.addUsers(users.map { userEntityMapper.mapToEntity(it) })
    }
}