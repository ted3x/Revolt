/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 5:32 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 5:32 PM
 */

package chat.revolt.data.local.mappers

import chat.revolt.data.local.entity.MetadataEntity
import chat.revolt.domain.models.Metadata
import chat.revolt.domain.models.MetadataType

class MetadataEntityMapper {

    fun mapToDomain(from: MetadataEntity): Metadata {
        return Metadata(
            type = MetadataType.valueOf(from.type),
            width = from.width,
            height = from.height
        )
    }

    fun mapToEntity(from: Metadata): MetadataEntity {
        return MetadataEntity(
            type = from.type.name,
            width = from.width,
            height = from.height
        )
    }
}