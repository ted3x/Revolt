/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 1:22 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 1:22 AM
 */

package chat.revolt.core.mapper

interface Mapper<F, T> {
    suspend fun map(from: F): T
}