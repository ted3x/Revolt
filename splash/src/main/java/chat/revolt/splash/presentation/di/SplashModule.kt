/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:50 PM
 */

package chat.revolt.splash.presentation.di

import chat.revolt.splash.data.api.SplashService
import chat.revolt.splash.data.data_source.SplashDataSource
import chat.revolt.splash.data.data_source.SplashDataSourceImpl
import chat.revolt.splash.data.mapper.RevoltConfigMapper
import chat.revolt.splash.data.repository.SplashRepositoryImpl
import chat.revolt.splash.domain.interactors.GetRevoltConfigUseCase
import chat.revolt.splash.domain.repository.SplashRepository
import chat.revolt.splash.presentation.ui.SplashFragment
import chat.revolt.splash.presentation.vm.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

private val splashViewModelModule = module {
    scope<SplashFragment> {
        viewModel {
            SplashViewModel(
                globalNavigator = get(),
                revoltConfigManager = get(),
                getRevoltConfigUseCase = get(),
                getUserUseCase = get(),
                addUserInDbUseCase = get(),
                accountRepository = get(),
            )
        }
    }
}

private val splashNetworkModule = module {
    scope<SplashFragment> {
        scoped<SplashService> { get<Retrofit>().create(SplashService::class.java) }
        scoped<SplashDataSource> { SplashDataSourceImpl(service = get()) }
        scoped<SplashRepository> { SplashRepositoryImpl(dataSource = get(), mapper = get()) }
        scoped { RevoltConfigMapper() }
        scoped { GetRevoltConfigUseCase(repository = get()) }
    }
}

val splashModules = splashNetworkModule + splashViewModelModule