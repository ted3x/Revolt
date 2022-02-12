/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 4:18 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 4:18 PM
 */

package chat.revolt.core

interface NetworkErrorHandler {
    fun handleException(exception: Throwable)
}