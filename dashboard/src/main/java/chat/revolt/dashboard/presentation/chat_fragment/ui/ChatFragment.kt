/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:24 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.core.paging_manager.LoadingAdapterListener
import chat.revolt.dashboard.databinding.ChatFragmentBinding
import chat.revolt.dashboard.presentation.chat_fragment.MessagesAdapter
import chat.revolt.dashboard.presentation.chat_fragment.di.chatModule
import chat.revolt.dashboard.presentation.chat_fragment.vm.ChatViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class ChatFragment :
    BaseFragment<ChatViewModel, ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    override val viewModel: ChatViewModel by viewModel()
    override val module: List<Module>
        get() = listOf(chatModule)
    private var isInitial = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MessagesAdapter(object : LoadingAdapterListener{
            override fun onLoadMore() { viewModel.loadMore() }
        })
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if(isInitial) {
                    binding.chatRecyclerView.scrollToPosition(0)
                    isInitial = false
                }
            }
        })
        val lm = LinearLayoutManager(context)
        lm.reverseLayout = true
        lm.stackFromEnd = true
        binding.chatRecyclerView.layoutManager = lm
        binding.chatRecyclerView.adapter = adapter
        viewModel.changeChannel("01FVSDSHJ6QSH0DZJYEBTZ2FES")
        viewModel.currentChannel.observe {
            isInitial = true
            lifecycleScope.launchWhenCreated {
                viewModel.flow.collectLatest {
                    adapter.submitList(it)
                }
            }
        }
    }
}