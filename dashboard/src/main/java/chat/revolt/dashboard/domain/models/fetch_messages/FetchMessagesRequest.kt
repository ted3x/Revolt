/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:48 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:48 AM
 */

package chat.revolt.dashboard.domain.models.fetch_messages


data class FetchMessagesRequest(
    val channelId: String,
    val limit: Int,
    val before: String? = null,
    val after: String? = null,
    val sort: String = "Latest",
    val includeUsers: Boolean = true
)