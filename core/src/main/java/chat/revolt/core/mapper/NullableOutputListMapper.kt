/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 7:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 7:50 PM
 */

package chat.revolt.core.mapper

interface NullableOutputListMapper<I, O>: Mapper<List<I>, List<O>?>

class NullableOutputListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : NullableOutputListMapper<I, O> {
    override suspend fun map(from: List<I>): List<O>? {
        return if (from.isEmpty()) null else from.map { mapper.map(it) }
    }
}