/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:24 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.core.PagedListDelegationAdapter
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.dashboard.databinding.ChatFragmentBinding
import chat.revolt.dashboard.databinding.TextAdapterItemBinding
import chat.revolt.dashboard.presentation.chat_fragment.di.chatModule
import chat.revolt.dashboard.presentation.chat_fragment.vm.ChatViewModel
import chat.revolt.domain.models.Message
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class ChatFragment : BaseFragment<ChatViewModel, ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    override val viewModel: ChatViewModel by viewModel()
    override val module: List<Module>
        get() = listOf(chatModule)
    object Comparator: DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PagedListDelegationAdapter(
            Comparator,
            textAdapterItem {

            },
        )
        var initial = true
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if(initial) {
                    binding.chatRecyclerView.scrollToPosition(itemCount - 1)
                    initial = false
                }
                viewModel.isLoading = false
            }
        })
        val lm = LinearLayoutManager(context)

        binding.chatRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentFirstVisible: Int = lm.findFirstVisibleItemPosition()
                if (currentFirstVisible < 5 && !viewModel.isLoading && dy < 0) {
                    viewModel.load()
                }
            }
        })
        binding.chatRecyclerView.layoutManager = lm
        binding.chatRecyclerView.adapter = adapter
        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.flow.collectLatest {
                    adapter.submitList(it)
                }
            }
        }
    }


    fun textAdapterItem(itemClickedListener: (Message) -> Unit) =
        adapterDelegateViewBinding<Message, Message, TextAdapterItemBinding>(
            { layoutInflater, root -> TextAdapterItemBinding.inflate(layoutInflater, root, false) }
        ) {
            bind {
                binding.authorName.text = item.author.username
                binding.text.text = item.content
            }
        }
}