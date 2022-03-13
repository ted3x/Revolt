/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 2:10 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 2:10 AM
 */

package chat.revolt.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import chat.revolt.app.di.appModules
import chat.revolt.core.BaseApp
import chat.revolt.socket.api.RevoltSocketListener
import chat.revolt.socket.api.ClientSocketManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.lang.ref.WeakReference


class RVApp : BaseApp(), Application.ActivityLifecycleCallbacks {

    var currentActivity: WeakReference<Activity>? = null
        private set

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RVApp)
            modules(appModules)
        }
        registerActivityLifecycleCallbacks(this)
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
}