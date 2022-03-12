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
import chat.revolt.dashboard.presentation.chat_fragment.adapter.MessagesAdapter
import chat.revolt.dashboard.presentation.chat_fragment.di.chatModule
import chat.revolt.dashboard.presentation.chat_fragment.vm.ChatViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class ChatFragment :
    BaseFragment<ChatViewModel, ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    override val viewModel: ChatViewModel by viewModel()
    override val module: List<Module>
        get() = listOf(chatModule)
    private var isInitial = false
    private var isWaitingForFetch: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MessagesAdapter(object : LoadingAdapterListener{
            override fun onLoadMore() {
                if(isWaitingForFetch == null) isWaitingForFetch = true
                else if(isWaitingForFetch == false) viewModel.loadMore()
            }
        })
        val lm = LinearLayoutManager(context)
        lm.reverseLayout = true
        lm.stackFromEnd = true
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if(isInitial || lm.findFirstCompletelyVisibleItemPosition() < 5) {
                    binding.chatRecyclerView.scrollToPosition(0)
                    isInitial = false
                }
            }
        })
        binding.chatRecyclerView.layoutManager = lm
        binding.chatRecyclerView.adapter = adapter
        viewModel.changeChannel("01FVSDSHJ6QSH0DZJYEBTZ2FES")
        lifecycleScope.launchWhenCreated {
            viewModel.initialMessages.collectLatest(adapter::setList)
        }
        viewModel.initialPhaseFinished.observe { finished ->
            if(finished) {
                lifecycleScope.launchWhenCreated {
                    viewModel.flow.collectLatest {
                        adapter.setList(it)
                        if (isWaitingForFetch == true) viewModel.loadMore()
                        isWaitingForFetch = false
                    }
                }
            }
        }
        viewModel.currentChannel.observe {
            isInitial = true
        }
        viewModel.typers.observe {
            binding.typers.text = it
            binding.typers.visibility = if (it != null) View.VISIBLE else View.GONE
        }
        viewModel.isEndReached.observe {
            adapter.isEndReached = it
        }
    }
}