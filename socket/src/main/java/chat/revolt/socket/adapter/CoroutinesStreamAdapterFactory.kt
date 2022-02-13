/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:17 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:17 PM
 */

package chat.revolt.socket.adapter

import com.tinder.scarlet.Stream
import com.tinder.scarlet.StreamAdapter
import com.tinder.scarlet.utils.getRawType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.collect
import java.lang.reflect.Type

class CoroutinesStreamAdapterFactory : StreamAdapter.Factory {

    private class ReceiveChannelStreamAdapter<T> : StreamAdapter<T, Flow<T>> {
        override fun adapt(stream: Stream<T>) = flow {
            stream.collect {
                emit(it)
            }
        }
    }

    override fun create(type: Type): StreamAdapter<Any, Any> {
        return when (type.getRawType()) {
            Flow::class.java -> ReceiveChannelStreamAdapter()
            else -> throw IllegalArgumentException()
        }
    }
}