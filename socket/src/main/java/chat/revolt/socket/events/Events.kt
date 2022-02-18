/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 10:27 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 10:27 PM
 */

package chat.revolt.socket.events

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class Events{
    open val type: Event? = null
}

enum class Event {
    Message
}