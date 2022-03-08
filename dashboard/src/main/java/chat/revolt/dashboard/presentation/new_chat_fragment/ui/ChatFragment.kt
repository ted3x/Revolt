/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:24 PM
 */

package chat.revolt.dashboard.presentation.new_chat_fragment.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.dashboard.databinding.ChatFragmentBinding
import chat.revolt.dashboard.presentation.asMergedLoadStates
import chat.revolt.dashboard.presentation.chat_fragment.LoadAdapter
import chat.revolt.dashboard.presentation.chat_fragment.MessagesAdapter
import chat.revolt.dashboard.presentation.new_chat_fragment.di.new_chatModule
import chat.revolt.dashboard.presentation.new_chat_fragment.vm.NewChatViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class NewChatFragment :
    BaseFragment<NewChatViewModel, ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    override val viewModel: NewChatViewModel by viewModel()
    override val module: List<Module>
        get() = listOf(new_chatModule)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MessagesAdapter()
        val lm = LinearLayoutManager(context)
        lm.reverseLayout = true
//        lm.stackFromEnd = true
        binding.chatRecyclerView.layoutManager = lm
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                Log.d("Loading", it.source.append.toString())
            }
        }
        val concatAdapter = adapter.withLoadStateFooter(footer = LoadAdapter())
        binding.chatRecyclerView.adapter = concatAdapter
        lifecycleScope.launchWhenCreated {
            viewModel.flow.collectLatest {
                adapter.submitData(it)
            }
        }

    }
}

