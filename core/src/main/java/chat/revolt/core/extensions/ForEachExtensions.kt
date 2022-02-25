/*
 * Created by Tedo Manvelidze(ted3x) on 2/20/22, 10:04 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/20/22, 10:04 PM
 */

package chat.revolt.core.extensions

suspend inline fun <T> Iterable<T>.forEach(crossinline action: suspend (T) -> Unit){
    this.forEach { action.invoke(it) }
}