/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 4:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 4:55 PM
 */

package chat.revolt.data.local.mappers

import chat.revolt.data.local.entity.AttachmentEntity
import chat.revolt.domain.models.Attachment

class AttachmentEntityMapper(private val metadataMapper: MetadataEntityMapper) :
    EntityDomainMapper<AttachmentEntity, Attachment> {

    override suspend fun mapToDomain(from: AttachmentEntity): Attachment {
        return Attachment(
            id = from.id,
            tag = from.tag,
            size = from.size,
            filename = from.filename,
            metadata = metadataMapper.mapToDomain(from.metadata),
            contentType = from.contentType
        )
    }

    override suspend fun mapToEntity(from: Attachment): AttachmentEntity {
        return AttachmentEntity(
            id = from.id,
            tag = from.tag,
            size = from.size,
            filename = from.filename,
            metadata = metadataMapper.mapToEntity(from.metadata),
            contentType = from.contentType
        )
    }
}