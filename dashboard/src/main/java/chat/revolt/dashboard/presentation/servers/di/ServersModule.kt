/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:51 PM
 */

package chat.revolt.dashboard.presentation.servers.di

import chat.revolt.dashboard.presentation.servers.ChannelManager
import chat.revolt.dashboard.presentation.servers.ServerManager
import chat.revolt.dashboard.presentation.servers.ui.ServersFragment
import chat.revolt.dashboard.presentation.servers.vm.ServersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val serversModule = module {
    scope<ServersFragment> {
        viewModel {
            ServersViewModel(
                userRepository = get(),
                channelManager = get(),
                serverManager = get()
            )
        }

        scoped { ServerManager(serversRepository = get(), userRepository = get(), membersRepository = get()) }
        scoped { ChannelManager(userRepository = get(), serverManager = get(), channelRepository = get()) }
    }
}