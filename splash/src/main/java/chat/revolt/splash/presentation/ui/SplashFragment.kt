/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 8:45 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 8:45 PM
 */

package chat.revolt.splash.presentation.ui

import android.os.Bundle
import android.view.View
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.splash.databinding.SplashFragmentBinding
import chat.revolt.splash.presentation.vm.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class SplashFragment :
    BaseFragment<SplashViewModel, SplashFragmentBinding>(SplashFragmentBinding::inflate) {
    override val viewModel: SplashViewModel by viewModel()
    override val module: List<Module>
        get() = TODO("Not yet implemented")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getServerConfig()
    }
}