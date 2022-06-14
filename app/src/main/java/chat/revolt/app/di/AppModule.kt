/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 2:05 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 2:05 PM
 */

package chat.revolt.app.di

import androidx.fragment.app.FragmentActivity
import chat.revolt.app.BuildConfig
import chat.revolt.app.MainActivity
import chat.revolt.app.global_navigator.GlobalNavigatorImpl
import chat.revolt.app.global_navigator.RVRouterImpl
import chat.revolt.app.loading_manager.LoadingManagerImpl
import chat.revolt.app.network.NetworkErrorHandlerImpl
import chat.revolt.app.network.NetworkStateManagerImpl
import chat.revolt.app.network.RevoltInterceptor
import chat.revolt.app.resource_provider.ResourceProviderImpl
import chat.revolt.app.revolt_config_manager.RevoltConfigManagerImpl
import chat.revolt.auth.navigator.AuthNavigator
import chat.revolt.auth.navigator.AuthNavigatorImpl
import chat.revolt.core.NetworkErrorHandler
import chat.revolt.core.NetworkState
import chat.revolt.core.loading_manager.LoadingManager
import chat.revolt.core.network.NetworkStateManager
import chat.revolt.core.resource_provider.ResourceProvider
import chat.revolt.core.server_config.RevoltConfigManager
import chat.revolt.core_datastore.SecurityUtil
import chat.revolt.core_datastore.UserPreferences
import chat.revolt.core_datastore.UserPreferencesImpl
import chat.revolt.core_navigation.navigator.GlobalNavigator
import chat.revolt.core_navigation.router.RVRouter
import chat.revolt.dashboard.navigator.DashboardNavigator
import chat.revolt.dashboard.navigator.DashboardNavigatorImpl
import chat.revolt.data.local.database.databaseModule
import chat.revolt.data.remote.di.userModule
import chat.revolt.data.remote.dto.message.MessageContentAdapter
import chat.revolt.data.repository.AccountRepositoryImpl
import chat.revolt.domain.repository.AccountRepository
import chat.revolt.socket.di.revoltSocketModule
import chat.revolt.splash.navigator.SplashNavigator
import chat.revolt.splash.navigator.SplashNavigatorImpl
import com.github.terrakok.cicerone.BaseRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.squareup.moshi.Moshi
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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
    single<GlobalNavigator> { GlobalNavigatorImpl(router = get()) }
}

val navigatorsModule = module {
    single<SplashNavigator> { SplashNavigatorImpl(router = get()) }
    single<AuthNavigator> { AuthNavigatorImpl(router = get()) }
    single<DashboardNavigator> { DashboardNavigatorImpl(router = get()) }
}

val resourceProviderModule = module {
    single<ResourceProvider> { ResourceProviderImpl(context = get()) }
}

val networkModule = module {
    single<NetworkErrorHandler> { NetworkErrorHandlerImpl(context = androidContext()) }
    single { Moshi.Builder().add(MessageContentAdapter()).build() }
    single { MoshiConverterFactory.create(get()) }
    single<AccountRepository> { AccountRepositoryImpl(accountDao = get()) }
    single { RevoltInterceptor(accountRepository = get()) }
    single { HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY } }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<RevoltInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder().baseUrl(BuildConfig.APP_BASE_URL)
            .addConverterFactory(get<MoshiConverterFactory>())
            .client(get())
            .build()
    }

    single<NetworkStateManager> { NetworkStateManagerImpl() }
}

val loadingManagerModule = module {
    single<LoadingManager> { LoadingManagerImpl(context = androidContext()) }
}

val revoltConfigModule = module {
    single<RevoltConfigManager> { RevoltConfigManagerImpl() }
}

val dataStoreModule = module {
    // missing inject datastore
    single { SecurityUtil() }
    single<UserPreferences> { UserPreferencesImpl(dataStore = get(), security = get())}
}
val appModules =
    listOf(
        globalNavigatorModule,
        navigatorsModule,
        resourceProviderModule,
        databaseModule,
        networkModule,
        loadingManagerModule,
        revoltConfigModule,
        revoltSocketModule,
        userModule,
        dataStoreModule
    )