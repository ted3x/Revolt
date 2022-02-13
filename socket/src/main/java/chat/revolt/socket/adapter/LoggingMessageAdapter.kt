/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:17 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 3:48 PM
 */

package chat.revolt.socket.adapter

import android.util.Log
import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import kotlinx.coroutines.flow.*
import java.lang.reflect.Type

class LoggingMessageAdapter<T>(
    private val messageAdapter: MessageAdapter<T>
) : MessageAdapter<T> {
    override fun fromMessage(message: Message): T {
        return messageAdapter.fromMessage(message).also {
            Log.d("Receive", "=> ${message.asString()}")
        }
    }

    override fun toMessage(data: T): Message =
        messageAdapter
            .toMessage(data)
            .also {
                Log.d("SEND", "=> ${it.asString()}")
            }

    private fun Message.asString(): String =
        when (this) {
            is Message.Text ->
                value
            is Message.Bytes ->
                value.toString()
        }

    class Factory(
        private val factory: MessageAdapter.Factory
    ) : MessageAdapter.Factory {
        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> =
            LoggingMessageAdapter(factory.create(type, annotations))
    }
}