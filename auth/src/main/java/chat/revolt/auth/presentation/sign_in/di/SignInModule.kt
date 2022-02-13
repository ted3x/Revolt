/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 2:26 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 2:26 PM
 */

package chat.revolt.auth.presentation.sign_in.di

import chat.revolt.auth.data.api.SignInService
import chat.revolt.auth.data.captcha_manager.CaptchaManagerImpl
import chat.revolt.auth.data.data_source.SignInDataSource
import chat.revolt.auth.data.data_source.SignInDataSourceImpl
import chat.revolt.auth.data.mapper.SignInMapper
import chat.revolt.auth.data.repository.SignInRepositoryImpl
import chat.revolt.auth.domain.captcha_manager.CaptchaManager
import chat.revolt.auth.domain.interactor.EmailValidation
import chat.revolt.auth.domain.interactor.PasswordValidation
import chat.revolt.auth.domain.interactor.SignInUseCase
import chat.revolt.auth.domain.repository.SignInRepository
import chat.revolt.auth.presentation.sign_in.ui.SignInFragment
import chat.revolt.auth.presentation.sign_in.vm.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

internal val signInViewModelModule = module {
    scope<SignInFragment> {
        viewModel {
            SignInViewModel(
                navigator = get(),
                passwordValidation = get(),
                emailValidation = get(),
                captchaManager = get(),
                signInUseCase = get(),
                revoltConfigManager = get()
            )
        }
        scoped { PasswordValidation(resourceProvider = get()) }
        scoped { EmailValidation(resourceProvider = get()) }
        scoped<CaptchaManager> { CaptchaManagerImpl(revoltConfigManager = get()) }
    }
}

internal val signInApiModule = module {
    single<SignInService> { get<Retrofit>().create(SignInService::class.java) }
    single<SignInDataSource> { SignInDataSourceImpl(service = get()) }
    single<SignInRepository> {
        SignInRepositoryImpl(
            dataSource = get(),
            mapper = get(),
        )
    }
    single { SignInMapper() }
    single { SignInUseCase(repository = get()) }
}

internal val signInModules = signInViewModelModule + signInApiModule

