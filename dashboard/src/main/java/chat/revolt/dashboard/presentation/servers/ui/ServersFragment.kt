/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:10 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:10 PM
 */

package chat.revolt.dashboard.presentation.servers.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.dashboard.databinding.ServersFragmentBinding
import chat.revolt.dashboard.presentation.servers.adapter.ServersAdapter
import chat.revolt.dashboard.presentation.servers.di.serversModule
import chat.revolt.dashboard.presentation.servers.vm.ServersViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class ServersFragment :
    BaseFragment<ServersViewModel, ServersFragmentBinding>(ServersFragmentBinding::inflate) {
    override val viewModel: ServersViewModel by viewModel()
    override val module: List<Module> = listOf(serversModule)
    private lateinit var adapter: ServersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ServersAdapter(onServerClick = {

        })
        binding.serversRv.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.servers.collectLatest {
                adapter.submitList(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            viewModel.currentUser.collectLatest {
                Glide.with(requireContext()).load(it.avatarUrl).into(binding.profile.avatar)
            }
        }
    }
}