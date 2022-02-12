/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 2:35 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 2:27 PM
 */

package chat.revolt.auth.presentation.sign_in.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import chat.revolt.auth.domain.captcha_manager.CaptchaListener
import chat.revolt.auth.domain.captcha_manager.CaptchaManager
import chat.revolt.auth.domain.interactor.EmailValidation
import chat.revolt.auth.domain.interactor.PasswordValidation
import chat.revolt.auth.domain.interactor.SignInUseCase
import chat.revolt.auth.domain.models.request.SignInRequest
import chat.revolt.auth.states.EmailStates
import chat.revolt.auth.states.PasswordStates
import chat.revolt.auth.utils.isValidEmail
import chat.revolt.auth.utils.isValidPassword
import chat.revolt.core.extensions.execute
import chat.revolt.core.server_config.RevoltConfigManager
import chat.revolt.core.view_model.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class SignInViewModel(
    revoltConfigManager: RevoltConfigManager,
    private val passwordValidation: PasswordValidation,
    private val emailValidation: EmailValidation,
    private val captchaManager: CaptchaManager,
    private val signInUseCase: SignInUseCase,
) :
    BaseViewModel() {

    val email = MutableLiveData<String>()
    val emailState = MutableStateFlow<EmailStates>(EmailStates.Valid)
    val password = MutableLiveData<String>()
    val passwordState = MutableStateFlow<PasswordStates>(PasswordStates.Valid)
    val captcha = MutableLiveData<String>()
    private val isCaptchaEnabled: Boolean = revoltConfigManager.getConfigFeatures().captcha.enabled

    private val captchaListener by lazy {
        object : CaptchaListener {
            override fun onSuccess(captcha: String) {
                this@SignInViewModel.captcha.value = captcha
            }

            override fun onFail(error: String?) {
                error?.let { snackBarMessage.value = it }
            }
        }
    }

    init {
        if (isCaptchaEnabled) captchaManager.setListener(captchaListener)
    }

    fun solveCaptcha(ctx: WeakReference<Context>) {
        if (email.value.isValidEmail() && password.value.isValidPassword()) {
            if (isCaptchaEnabled) {
                captchaManager.solveCaptcha(ctx)
            } else {
                signIn()
            }
        } else {
            validateEmail(email.value)
            validatePassword(password.value)
        }
    }

    fun onEmailTextFieldChange(email: String) {
        this@SignInViewModel.email.value = email
    }

    fun onPasswordTextFieldChange(password: String) {
        this@SignInViewModel.password.value = password
    }

    fun validateEmail(email: String?) {
        viewModelScope.launch {
            emailState.emit(emailValidation.invoke(email))
        }
    }

    fun validatePassword(password: String?) {
        viewModelScope.launch {
            passwordState.emit(passwordValidation.invoke(password))
        }
    }

    fun signIn() {
        viewModelScope.launch {
            val request = SignInRequest(
                email = email.value!!,
                password = password.value!!,
                captcha = if (isCaptchaEnabled) captcha.value else null
            )
            signInUseCase.execute(params = request,
                onLoading = { loadingManager.toggleLoading(it) },
                onSuccess = {
                    TODO("navigateToDashboard")
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        captchaManager.removeListener()
    }
}