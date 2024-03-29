/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 4:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 4:55 PM
 */

package chat.revolt.data.remote.mappers.message

import chat.revolt.data.remote.dto.AttachmentDto
import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.data.remote.mappers.MetadataMapper
import chat.revolt.domain.models.Attachment
import chat.revolt.domain.models.Message

class AttachmentMapper(private val metadataMapper: MetadataMapper) {

    fun mapToDomain(from: AttachmentDto): Attachment {
        return Attachment(
            id = from.id,
            tag = from.tag,
            size = from.size,
            filename = from.filename,
            metadata = metadataMapper.mapToDomain(from.metadata),
            contentType = from.contentType
        )
    }

    fun mapToDto(from: Attachment): AttachmentDto {
        return AttachmentDto(
            id = from.id,
            tag = from.tag,
            size = from.size,
            filename = from.filename,
            metadata = metadataMapper.mapToDto(from.metadata),
            contentType = from.contentType
        )
    }
}