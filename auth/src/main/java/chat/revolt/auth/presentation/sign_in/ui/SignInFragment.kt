/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 2:26 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 12:09 AM
 */

package chat.revolt.auth.presentation.sign_in.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import chat.revolt.auth.databinding.SignInFragmentBinding
import chat.revolt.auth.presentation.sign_in.di.signInModules
import chat.revolt.auth.presentation.sign_in.vm.SignInViewModel
import chat.revolt.auth.states.EmailStates
import chat.revolt.auth.states.PasswordStates
import chat.revolt.core.extensions.onChange
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.socket.api.RevoltSocketImpl
import chat.revolt.socket.server.channel.ChannelEventManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module
import java.lang.ref.WeakReference

class SignInFragment :
    BaseFragment<SignInViewModel, SignInFragmentBinding>(SignInFragmentBinding::inflate) {

    override val viewModel: SignInViewModel by viewModel()
    override val module: List<Module> = signInModules
    val socket = get<RevoltSocketImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signIn.setOnClickListener { viewModel.solveCaptcha(WeakReference(requireActivity())) }
        setupTextFieldListeners()
        setupStateListeners()
        setupObservers()
    }

    private fun setupTextFieldListeners() {
        binding.emailEditText.onChange(viewModel::onEmailTextFieldChange)
        binding.passwordEditText.onChange(viewModel::onPasswordTextFieldChange)
    }

    private fun setupStateListeners() {
        lifecycleScope.launchWhenResumed {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.emailState.collect { onEmailStateChange(it) } }
                launch { viewModel.passwordState.collect { onPasswordStateChange(it) } }
                launch { socket.start() }
                launch {
                    get<ChannelEventManager>().onChannelStartTyping().collect {
                        println(it)
                    }
                }
                launch {
                    get<ChannelEventManager>().onChannelStopTyping().collect {
                        println(it)
                    }
                }
            }
        }
    }

    private fun onEmailStateChange(emailStates: EmailStates) {
        when (emailStates) {
            is EmailStates.Valid -> binding.textField.error = null
            is EmailStates.Invalid -> binding.textField.error = emailStates.message
        }
    }

    private fun onPasswordStateChange(passwordStates: PasswordStates) {
        when (passwordStates) {
            is PasswordStates.Valid -> binding.passwordTextField.error = null
            is PasswordStates.Short -> binding.passwordTextField.error = passwordStates.message
            is PasswordStates.Long -> binding.passwordTextField.error = passwordStates.message
        }
    }

    private fun setupObservers() {
        viewModel.email.observe { viewModel.validateEmail(it) }
        viewModel.password.observe { viewModel.validatePassword(it) }
        viewModel.captcha.observe { viewModel.signIn() }
    }
}