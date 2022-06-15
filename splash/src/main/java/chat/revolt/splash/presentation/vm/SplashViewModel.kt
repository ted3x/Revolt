/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 8:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 8:53 PM
 */

package chat.revolt.splash.presentation.vm

import android.util.Log
import androidx.lifecycle.viewModelScope
import chat.revolt.core.extensions.execute
import chat.revolt.core.server_config.RevoltConfigManager
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.core_navigation.features.Feature
import chat.revolt.core_navigation.features.auth.AuthStates
import chat.revolt.core_navigation.features.dashboard.DashboardStates
import chat.revolt.core_navigation.navigator.GlobalNavigator
import chat.revolt.domain.interactors.AddUserInDbUseCase
import chat.revolt.domain.interactors.GetUserUseCase
import chat.revolt.domain.repository.AccountRepository
import chat.revolt.socket.api.ClientSocketManager
import chat.revolt.socket.api.RevoltSocketListener
import chat.revolt.socket.di.revoltSocketModule
import chat.revolt.splash.domain.interactors.GetRevoltConfigUseCase
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get

class SplashViewModel(
    private val globalNavigator: GlobalNavigator,
    private val getRevoltConfigUseCase: GetRevoltConfigUseCase,
    private val revoltConfigManager: RevoltConfigManager,
    private val getUserUseCase: GetUserUseCase,
    private val addUserInDbUseCase: AddUserInDbUseCase,
    private val accountRepository: AccountRepository,
) : BaseViewModel(), RevoltSocketListener {

    private var isUserLoggedIn = false
    fun fetchRevoltConfig() {
        viewModelScope.launch {
            getRevoltConfigUseCase.execute(params = Unit,
                onSuccess = { revoltConfig ->
                    revoltConfigManager.setConfig(revoltConfig)
                    accountRepository.getUserId()?.let { fetchUser(it) } ?: initializeSocket()
                }
            )
        }
    }

    private suspend fun fetchUser(userId: String) {
        getUserUseCase.execute(params = userId, onSuccess = {
            addUserInDbUseCase.execute(params = it!!,
                onSuccess = {
                    isUserLoggedIn = true
                    initializeSocket()
                }
            )
        })
    }

    private fun initializeSocket() {
        viewModelScope.launch {
            // workaround because when created in constructor, socket instance is created before initializer
            // so socket fails and skips onOpen
            get<ClientSocketManager>(ClientSocketManager::class.java).initialize(
                this@SplashViewModel
            )
        }
    }

    override fun onConnectionOpened() {
        if(isUserLoggedIn) globalNavigator.navigateTo(Feature.Dashboard(state = DashboardStates.Dashboard))
        else globalNavigator.navigateTo(Feature.Auth(state = AuthStates.SignIn))
    }

    override fun onMessageReceived(message: String) {
        Log.d("onMessageReceived", message)
    }

    override fun onConnectionClosing() {
        Log.d("onConnectionClosing", "*******************")
    }

    override fun onConnectionClosed() {
        Log.d("onConnectionClosed", "*******************")
    }

    override fun onConnectionFailed() {
        Log.d("onConnectionFailed", "*******************")
    }
}