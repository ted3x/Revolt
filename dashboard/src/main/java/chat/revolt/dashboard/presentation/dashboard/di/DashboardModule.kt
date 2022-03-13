/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:22 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:22 PM
 */

package chat.revolt.dashboard.presentation.dashboard.di

import chat.revolt.dashboard.presentation.dashboard.vm.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
    viewModel { DashboardViewModel(networkStateManager = get()) }
}