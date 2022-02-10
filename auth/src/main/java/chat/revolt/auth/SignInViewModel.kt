/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:37 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:31 AM
 */

package chat.revolt.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import chat.revolt.auth.data.captcha_manager.CaptchaManagerImpl
import chat.revolt.auth.domain.captcha_manager.CaptchaListener
import chat.revolt.auth.domain.captcha_manager.CaptchaManager
import chat.revolt.auth.domain.interactor.EmailValidation
import chat.revolt.auth.domain.interactor.PasswordValidation
import chat.revolt.auth.states.EmailStates
import chat.revolt.auth.states.PasswordStates
import chat.revolt.auth.utils.isValidEmail
import chat.revolt.auth.utils.isValidPassword
import chat.revolt.core.view_model.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class SignInViewModel(
    private val passwordValidation: PasswordValidation,
    private val emailValidation: EmailValidation,
    private val captchaManager: CaptchaManager = CaptchaManagerImpl(""),
) :
    BaseViewModel() {

    val email = MutableLiveData<String>()
    val emailState = MutableStateFlow<EmailStates>(EmailStates.Valid)
    val password = MutableLiveData<String>()
    val passwordState = MutableStateFlow<PasswordStates>(PasswordStates.Valid)
    val captcha = MutableLiveData<String>()

    private val captchaListener = object : CaptchaListener {
        override fun onSuccess(captcha: String) {
            this@SignInViewModel.captcha.value = captcha
        }

        override fun onFail(error: String?) {
            error?.let { snackBarMessage.value = it }
        }
    }

    init {
        captchaManager.setListener(captchaListener)
    }

    fun solveCaptcha(ctx: WeakReference<Context>) {
        if (email.value.isValidEmail() && password.value.isValidPassword())
            captchaManager.solveCaptcha(ctx)
        else {
            validateEmail(email.value)
            validatePassword(password.value)
        }
    }

    fun signIn() {
        TODO()
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

    override fun onCleared() {
        super.onCleared()
        captchaManager.removeListener()
    }
}