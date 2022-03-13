/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:19 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:19 PM
 */

package chat.revolt.dashboard.presentation.dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import chat.revolt.core.NetworkState
import chat.revolt.core.extensions.visibleIf
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.dashboard.R
import chat.revolt.dashboard.databinding.DashboardFragmentBinding
import chat.revolt.dashboard.presentation.chat_fragment.ui.ChatFragment
import chat.revolt.dashboard.presentation.dashboard.di.dashboardModule
import chat.revolt.dashboard.presentation.dashboard.vm.DashboardViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class DashboardFragment :
    BaseFragment<DashboardViewModel, DashboardFragmentBinding>(DashboardFragmentBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()
    override val module: List<Module> = listOf(dashboardModule)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        childFragmentManager.beginTransaction().add(binding.centerPanel.id, ChatFragment()).commit()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.networkStateManager.getStateFlow().collectLatest {
                when (it) {
                    is NetworkState.Connected -> binding.networkUnavailable.root.visibleIf(false)
                    is NetworkState.Disconnected -> binding.networkUnavailable.root.visibleIf(true)
                        .also {
                            binding.networkUnavailable.message.text = requireContext().getString(
                                R.string.network_connectivity_limited_or_unavailable
                            )
                        }
                    is NetworkState.NoInternet -> binding.networkUnavailable.root.visibleIf(true)
                        .also { binding.networkUnavailable.message.text = "Connecting..." }
                }
            }
        }
    }
}