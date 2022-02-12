/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 2:05 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 2:05 PM
 */

package chat.revolt.app.di

import androidx.fragment.app.FragmentActivity
import chat.revolt.app.MainActivity
import chat.revolt.app.global_navigator.GlobalNavigatorImpl
import chat.revolt.app.global_navigator.RVRouterImpl
import chat.revolt.app.network.NetworkErrorHandlerImpl
import chat.revolt.app.network.RevoltAuthenticator
import chat.revolt.app.resource_provider.ResourceProviderImpl
import chat.revolt.core.NetworkErrorHandler
import chat.revolt.core.resource_provider.ResourceProvider
import chat.revolt.core_navigation.navigator.GlobalNavigator
import chat.revolt.core_navigation.router.RVRouter
import chat.revolt.data.local.database.databaseModule
import chat.revolt.data.repository.AccountRepositoryImpl
import chat.revolt.domain.repository.AccountRepository
import com.github.terrakok.cicerone.BaseRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import okhttp3.Authenticator
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

val resourceProviderModule = module {
    single<ResourceProvider> { ResourceProviderImpl(context = get()) }
}

val networkModule = module {
    single<NetworkErrorHandler> { NetworkErrorHandlerImpl(context = androidContext()) }
    single { MoshiConverterFactory.create() }
    single<AccountRepository> { AccountRepositoryImpl(accountDao = get()) }
    single<Authenticator> { RevoltAuthenticator(accountRepository = get()) }
    single { HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY } }
    single {
        OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>()).authenticator(get())
            .build()
    }
    single {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(get<MoshiConverterFactory>())
            .client(get())
            .build()
    }
}
val appModules = globalNavigatorModule + resourceProviderModule + databaseModule + networkModule

const val BASE_URL = "https://api.revolt.chat/"