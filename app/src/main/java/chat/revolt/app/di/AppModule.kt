/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 2:05 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 2:05 PM
 */

package chat.revolt.app.di

import androidx.fragment.app.FragmentActivity
import chat.revolt.app.MainActivity
import chat.revolt.app.global_navigator.RVRouterImpl
import chat.revolt.app.resource_provider.ResourceProviderImpl
import chat.revolt.core.resource_provider.ResourceProvider
import chat.revolt.core_navigation.router.RVRouter
import com.github.terrakok.cicerone.BaseRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.dsl.binds
import org.koin.dsl.module

val globalNavigatorModule = module {
    single { RVRouterImpl() } binds arrayOf(BaseRouter::class, RVRouter::class)
    single { Cicerone.create(customRouter = get()) }
    scope<MainActivity> {
        scoped { get<Cicerone<Router>>().getNavigatorHolder() }
        scoped { (activity: FragmentActivity, containerId: Int) ->
            AppNavigator(
                activity = activity,
                containerId = containerId
            )
        }
    }
}

val resourceProviderModule = module {
    single<ResourceProvider> { ResourceProviderImpl(context = get()) }
}

val appModules = globalNavigatorModule + resourceProviderModule
