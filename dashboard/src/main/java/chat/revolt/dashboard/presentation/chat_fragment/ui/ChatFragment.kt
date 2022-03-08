/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:24 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.dashboard.databinding.ChatFragmentBinding
import chat.revolt.dashboard.presentation.asMergedLoadStates
import chat.revolt.dashboard.presentation.chat_fragment.LoadAdapter
import chat.revolt.dashboard.presentation.chat_fragment.MessagesAdapter
import chat.revolt.dashboard.presentation.chat_fragment.di.chatModule
import chat.revolt.dashboard.presentation.chat_fragment.vm.ChatViewModel
import chat.revolt.domain.models.Message
import chat.revolt.styles.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class ChatFragment :
    BaseFragment<ChatViewModel, ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    private lateinit var messageCollectorJob: Job
    override val viewModel: ChatViewModel by viewModel()
    override val module: List<Module>
        get() = listOf(chatModule)

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val adapter = MessagesAdapter()
//        val lm = LinearLayoutManager(context)
//        lm.reverseLayout = true
//        binding.chatRecyclerView.layoutManager = lm
//        binding.chatRecyclerView.adapter = adapter.withLoadStateHeader(header = LoadAdapter())
//        lifecycleScope.launchWhenCreated {
////            viewModel.flow?.collectLatest {
////                adapter.submitData(it)
////            }
//        }
//        lifecycleScope.launchWhenCreated {
//            adapter.loadStateFlow
//                // Use a state-machine to track LoadStates such that we only transition to
//                // NotLoading from a RemoteMediator load if it was also presented to UI.
//                .asMergedLoadStates()
//                // Only emit when REFRESH changes, as we only want to react on loads replacing the
//                // list.
//                .distinctUntilChangedBy { it.refresh }
//                // Only react to cases where REFRESH completes i.e., NotLoading.
//                .filter { it.refresh is LoadState.NotLoading }
//                // Scroll to top is synchronous with UI updates, even if remote load was triggered.
//                .collect { binding.chatRecyclerView.scrollToPosition(0) }
//        }
//    }
}