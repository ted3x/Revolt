/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 7:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 7:50 PM
 */

package chat.revolt.core.mapper

interface NullableInputListMapper<I, O>: Mapper<List<I>?, List<O>>

class NullableInputListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : NullableInputListMapper<I, O> {
    override fun map(from: List<I>?): List<O> {
        return from?.map { mapper.map(it) }.orEmpty()
    }
}