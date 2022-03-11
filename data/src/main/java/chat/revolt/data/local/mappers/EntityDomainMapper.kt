/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 12:23 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 12:23 AM
 */

package chat.revolt.data.local.mappers

interface EntityDomainMapper<E, D> {
    suspend fun mapToDomain(from: E): D
    suspend fun mapToDomain(from: List<E>): List<D> {
        return from.map { mapToDomain(it) }
    }

    suspend fun mapToEntity(from: D): E
    suspend fun mapToEntity(from: List<D>): List<E> {
        return from.map { mapToEntity(it) }
    }
}