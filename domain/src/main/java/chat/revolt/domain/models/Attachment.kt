/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:22 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:22 AM
 */

package chat.revolt.domain.models

data class Attachment(
    val id: String,
    val tag: String,
    val size: String,
    val filename: String,
    val metadata: Metadata,
    val contentType: String?
) {
    val url get() = "$ATTACHMENT_BASE_URL/$tag/$id?width=${metadata.width}&height=${metadata.height}"

    companion object {
        private const val ATTACHMENT_BASE_URL = "https://autumn.revolt.chat"
    }
}