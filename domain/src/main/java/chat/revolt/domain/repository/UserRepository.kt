/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 5:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 5:55 PM
 */

package chat.revolt.domain.repository

import chat.revolt.domain.models.User

interface UserRepository {

    suspend fun getUser(userId: String): User
    suspend fun getCurrentUser(): User
    suspend fun getMessageAuthor(authorId: String, users: List<User>): User
    suspend fun getUsers(userIds: List<String>): List<User>
    suspend fun addUser(user: User)
    suspend fun addUsers(users: List<User>)
}