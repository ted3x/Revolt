/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 5:11 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 5:11 PM
 */

package chat.revolt.domain.models

enum class MetadataType {
    File,
    Text,
    Audio,
    Image,
    Video
}
data class Metadata(
    val type: MetadataType,
    val width: Int?,
    val height: Int?
)