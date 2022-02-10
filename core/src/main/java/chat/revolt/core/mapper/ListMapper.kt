/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 7:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 7:48 PM
 */

package chat.revolt.core.mapper

interface ListMapper<I, O>: Mapper<List<I>, List<O>>

class ListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : ListMapper<I, O> {
    override fun map(from: List<I>): List<O> {
        return from.map { mapper.map(it) }
    }
}