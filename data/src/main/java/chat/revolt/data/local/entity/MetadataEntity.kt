/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 5:10 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 5:10 PM
 */

package chat.revolt.data.local.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetadataEntity(
    val type: String,
    val width: Int?,
    val height: Int?
)