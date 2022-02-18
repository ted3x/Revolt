/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 11:03 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 11:03 PM
 */

package chat.revolt.socket.api

interface RevoltSocketListener {

    fun onConnectionOpened()
    fun onMessageReceived(message: String)
    fun onConnectionClosing()
    fun onConnectionClosed()
    fun onConnectionFailed()
}