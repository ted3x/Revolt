/*
 * Created by Tedo Manvelidze(ted3x) on 2/19/22, 11:56 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/19/22, 11:56 PM
 */

package chat.revolt.domain.models

enum class ContentType {
    Message,
    Text,
    UserAdded,
    UserRemove,
    UserJoined,
    UserLeft,
    UserKicked,
    UserBanned,
    ChannelRenamed,
    ChannelDescriptionChanged,
    ChannelIconChanged,
}