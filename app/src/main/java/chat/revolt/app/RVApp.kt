/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 2:10 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 2:10 AM
 */

package chat.revolt.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import chat.revolt.app.di.appModules
import chat.revolt.socket.api.RevoltSocketListener
import chat.revolt.socket.api.ClientSocketManager
import chat.revolt.socket.client.SocketAPI
import chat.revolt.socket.client.data.AuthenticateRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.lang.ref.WeakReference

class RVApp : Application(), Application.ActivityLifecycleCallbacks, RevoltSocketListener {

    var currentActivity: WeakReference<Activity>? = null
        private set

    private val socketManager by inject<ClientSocketManager>()
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RVApp)
            modules(appModules)
        }
        registerActivityLifecycleCallbacks(this)
        GlobalScope.launch { socketManager.initialize(this@RVApp) }
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {}

    override fun onActivityStarted(p0: Activity) {}

    override fun onActivityResumed(p0: Activity) {
        currentActivity = WeakReference(p0)
    }

    override fun onActivityPaused(p0: Activity) {
        currentActivity = null
    }

    override fun onActivityStopped(p0: Activity) {}

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}

    override fun onActivityDestroyed(p0: Activity) {}

    override fun onConnectionOpened() {
        Log.d("onConnectionOpened", "*******************")
        val socket by inject<SocketAPI>()
        socket.authenticate(AuthenticateRequest(token = "-RMd3HjT0-PhSZY7tGwKFy8lSx6KtnZHTyLo5wdR8sPOXE_4y7qol0JdrKZOWmwE"))
    }

    override fun onMessageReceived(message: String) {
        Log.d("onMessageReceived", message)
    }

    override fun onConnectionClosing() {
        Log.d("onConnectionClosing", "*******************")
    }

    override fun onConnectionClosed() {
        Log.d("onConnectionClosed", "*******************")
    }

    override fun onConnectionFailed() {
        Log.d("onConnectionFailed", "*******************")
    }
}