/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 8:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 8:53 PM
 */

package chat.revolt.splash.presentation.vm

import androidx.lifecycle.viewModelScope
import chat.revolt.core.extensions.execute
import chat.revolt.core.server_config.RevoltConfigManager
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.core_navigation.features.Feature
import chat.revolt.core_navigation.features.auth.AuthStates
import chat.revolt.core_navigation.navigator.GlobalNavigator
import chat.revolt.domain.interactors.AddUserInDbUseCase
import chat.revolt.domain.interactors.GetUserUseCase
import chat.revolt.splash.domain.interactors.GetRevoltConfigUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val globalNavigator: GlobalNavigator,
    private val getRevoltConfigUseCase: GetRevoltConfigUseCase,
    private val revoltConfigManager: RevoltConfigManager,
    private val getUserUseCase: GetUserUseCase,
    private val addUserInDbUseCase: AddUserInDbUseCase
) : BaseViewModel() {

    fun fetchRevoltConfig() {
        viewModelScope.launch {
            getRevoltConfigUseCase.execute(params = Unit,
                onSuccess = { revoltConfig ->
                    revoltConfigManager.setConfig(revoltConfig)
                    fetchUser()
                }
            )
        }
    }

    private suspend fun fetchUser() {
        getUserUseCase.execute(params = "01FVDATAJD2V5XTSZQ2B9DA876", onSuccess = {
            addUserInDbUseCase.execute(params = it!!,
                onSuccess = {
                    globalNavigator.navigateTo(Feature.Auth(state = AuthStates.SignIn))
                })
        })
    }
}