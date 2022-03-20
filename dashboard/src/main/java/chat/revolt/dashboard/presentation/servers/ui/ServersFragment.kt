/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:10 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:10 PM
 */

package chat.revolt.dashboard.presentation.servers.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import chat.revolt.core.extensions.load
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.dashboard.databinding.ServersFragmentBinding
import chat.revolt.dashboard.presentation.dashboard.ui.ChannelChangeListener
import chat.revolt.dashboard.presentation.servers.adapter.ServersAdapter
import chat.revolt.dashboard.presentation.servers.adapter.channels.ChannelsAdapter
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

        initializeServers()
        initializeServerObservers()
        initializeChannels()
        initializeUserObserver()
    }

    private fun initializeServers() {
        adapter = ServersAdapter(onServerClick = {
            viewModel.changeServer(it)
        })
        binding.serversRv.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.servers.collectLatest {
                adapter.submitList(it)
            }
        }
    }

    private fun initializeServerObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.serverName.collectLatest(binding.server.serverName::setText)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.serverBadgeRes.collectLatest(::updateBadge)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.serverBanner.collectLatest(::updateBanner)
        }
    }

    private fun initializeChannels() {
        val channelsAdapter =
            ChannelsAdapter(onCategoryVisibilityChange = { categoryId ->
                viewModel.onCategoryVisibilityChange(categoryId)
            }, onChannelClick = {
                viewModel.onChannelClick(it)
                (parentFragment as ChannelChangeListener).onChannelChange(it)
            })
        binding.server.channels.adapter = channelsAdapter
        binding.server.channels.itemAnimator = null
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.channels.collectLatest {
                channelsAdapter.submitList(it)
            }
        }
    }

    private fun initializeUserObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.currentUserImageUrl.collectLatest { url ->
                url?.let { binding.profile.avatar.load(it) }
            }
        }
    }

    private fun updateBadge(badge: Int?){
        if(badge == null) {
            binding.server.serverBadge.visibility = View.GONE
        }
        else {
            binding.server.serverBadge.setBackgroundResource(badge)
            binding.server.serverBadge.visibility = View.VISIBLE
        }
    }

    private fun updateBanner(banner: String?) {
        if (banner == null) {
            binding.server.serverBanner.visibility = View.GONE
            binding.server.bannerShadow.visibility = View.GONE
        } else {
            binding.server.serverBanner.visibility = View.VISIBLE
            binding.server.bannerShadow.visibility = View.VISIBLE
            Glide.with(requireContext()).load(banner).into(binding.server.serverBanner)
        }
    }
}