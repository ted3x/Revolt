/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 5:13 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 5:13 PM
 */

package chat.revolt.data.remote.mappers

import chat.revolt.data.remote.dto.MetadataDto
import chat.revolt.domain.models.Metadata
import chat.revolt.domain.models.MetadataType

class MetadataMapper {

    fun mapToDomain(from: MetadataDto): Metadata {
        return Metadata(
            type = MetadataType.valueOf(from.type),
            width = from.width,
            height = from.height
        )
    }

    fun mapToDto(from: Metadata): MetadataDto {
        return MetadataDto(
            type = from.type.name,
            width = from.width,
            height = from.height
        )
    }
}