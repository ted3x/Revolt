/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 3:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 3:38 PM
 */

package chat.revolt.core.mapper

interface ListMapper<I, O>: Mapper<List<I>, List<O>>

class ListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : ListMapper<I, O> {
    override suspend fun map(from: List<I>): List<O> {
        return from.map { mapper.map(it) }
    }
}