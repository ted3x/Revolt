/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:24 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.core.extensions.hiddenIf
import chat.revolt.core.extensions.visibleIf
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.core.paging_manager.LoadingAdapterListener
import chat.revolt.dashboard.databinding.ChatFragmentBinding
import chat.revolt.dashboard.presentation.chat_fragment.adapter.MessagesAdapter
import chat.revolt.dashboard.presentation.chat_fragment.adapter.decorator.MessagesDecorator
import chat.revolt.dashboard.presentation.chat_fragment.di.chatModule
import chat.revolt.dashboard.presentation.chat_fragment.vm.ChatViewModel
import chat.revolt.dashboard.presentation.dashboard.ui.ChannelChangeListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class ChatFragment :
    BaseFragment<ChatViewModel, ChatFragmentBinding>(ChatFragmentBinding::inflate), ChannelChangeListener {

    override val viewModel: ChatViewModel by viewModel()
    override val module: List<Module>
        get() = listOf(chatModule)
    private var isInitial = false
    private var isWaitingForFetch: Boolean? = null
    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MessagesAdapter(object : LoadingAdapterListener {
            override fun onLoadMore() {
                if (isWaitingForFetch == null) isWaitingForFetch = true
                else if (isWaitingForFetch == false) viewModel.loadMore()
            }
        })
        val lm = LinearLayoutManager(context)
        lm.reverseLayout = true
        lm.stackFromEnd = true
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (isInitial || lm.findFirstCompletelyVisibleItemPosition() < 5) {
                     binding.chatRecyclerView.scrollToPosition(0)
                    isInitial = false
                }
            }
        })
        binding.chatRecyclerView.addItemDecoration(MessagesDecorator())
        binding.chatRecyclerView.layoutManager = lm
        binding.chatRecyclerView.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.initialMessages.collectLatest(adapter::setList)
        }
        viewModel.initialPhaseFinished.observe { finished ->
            if (finished) {
                job = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.flow.cancellable().collectLatest {
                        adapter.setList(it)
                        if (isWaitingForFetch == true) viewModel.loadMore()
                        isWaitingForFetch = false
                    }
                }
            }
        }
        viewModel.currentChannel.observe {
            setChannelName(it.name)
            job?.cancelChildren()
            isInitial = true
        }
        viewModel.typers.observe {
            binding.typers.text = it
            binding.typers.visibleIf { it != null }
        }
        viewModel.isEndReached.observe {
            adapter.isEndReached = it
        }
        binding.send.setOnClickListener {
            viewModel.sendMessage(binding.input.text.toString())
            binding.input.editableText.clear()
        }
        binding.input.doOnTextChanged { _, _, _, _ ->
            binding.send.visibleIf { !binding.input.text.isNullOrBlank() }
        }
    }

    private fun setChannelName(channelName: String) {
        binding.header.channelName.text = channelName
        binding.input.hint = "Message $channelName"
    }

    override fun onChannelChange(serverId: String, channelId: String) {
        if(viewModel.currentChannel.value?.id != channelId)
            viewModel.changeChannel(serverId, channelId)
    }
}