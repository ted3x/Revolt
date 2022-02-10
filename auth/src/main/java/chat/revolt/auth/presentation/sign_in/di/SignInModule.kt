/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 2:26 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 2:26 PM
 */

package chat.revolt.auth.presentation.sign_in.di

import chat.revolt.auth.data.captcha_manager.CaptchaManagerImpl
import chat.revolt.auth.domain.captcha_manager.CaptchaManager
import chat.revolt.auth.domain.interactor.EmailValidation
import chat.revolt.auth.domain.interactor.PasswordValidation
import chat.revolt.auth.presentation.sign_in.ui.SignInFragment
import chat.revolt.auth.presentation.sign_in.vm.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val signInModule = module {
    scope<SignInFragment> {
        viewModel {
            SignInViewModel(
                passwordValidation = get(),
                emailValidation = get(),
                captchaManager = get()
            )
        }
        scoped { PasswordValidation(resourceProvider = get()) }
        scoped { EmailValidation(resourceProvider = get()) }
        scoped<CaptchaManager> { CaptchaManagerImpl() }
    }
}