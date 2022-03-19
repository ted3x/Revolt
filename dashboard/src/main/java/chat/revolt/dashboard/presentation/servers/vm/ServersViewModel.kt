/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:51 PM
 */

package chat.revolt.dashboard.presentation.servers.vm

import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.domain.repository.ServerRepository
import chat.revolt.domain.repository.UserRepository

class ServersViewModel(
    private val userRepository: UserRepository,
    private val serversRepository: ServerRepository
) : BaseViewModel() {

    val currentUser = userRepository.getCurrentUserAsFlow()
    val servers = serversRepository.getServers()
}