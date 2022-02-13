/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 6:16 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 6:16 PM
 */

package chat.revolt.core.extensions

import chat.revolt.core.NetworkErrorHandler
import chat.revolt.domain.interactors.BaseUseCase
import org.koin.java.KoinJavaComponent.get

suspend fun <I, O> BaseUseCase<I, O>.execute(
    errorHandler: NetworkErrorHandler = get(NetworkErrorHandler::class.java),
    params: I,
    onLoading: (suspend (isLoading: Boolean) -> Unit)? = null,
    onSuccess: suspend (O) -> Unit,
    onError: (suspend (Throwable) -> Unit)? = null
) {
    onLoading?.invoke(true)
    try {
        onSuccess.invoke(this.invoke(params))
    }
    catch (throwable: Throwable) {
        onError?.invoke(throwable) ?: errorHandler.handleException(throwable)
    }
    finally {
        onLoading?.invoke(false)
    }
}