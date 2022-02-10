/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 2:10 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 2:10 AM
 */

package chat.revolt.app

import android.app.Application
import chat.revolt.app.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RVApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RVApp)
            modules(appModules)
        }
    }
}