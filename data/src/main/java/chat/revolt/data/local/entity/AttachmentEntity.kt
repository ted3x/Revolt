/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:19 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:19 AM
 */

package chat.revolt.data.local.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttachmentEntity(
    val id: String,
    val tag: String,
    val size: String,
    val filename: String,
    val metadata: MetadataEntity,
    val contentType: String?
)