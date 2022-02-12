/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 2:10 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 2:10 AM
 */

package chat.revolt.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import chat.revolt.app.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.lang.ref.WeakReference

class RVApp : Application(), Application.ActivityLifecycleCallbacks {

    var currentActivity: WeakReference<Activity>? = null
        private set

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RVApp)
            modules(appModules)
        }
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