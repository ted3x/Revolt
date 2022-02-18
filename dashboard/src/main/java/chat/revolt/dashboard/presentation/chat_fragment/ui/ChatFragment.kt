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
import chat.revolt.core.UlidTimeDecoder
import chat.revolt.core.extensions.toDate
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.dashboard.databinding.ChatFragmentBinding
import chat.revolt.dashboard.databinding.TextAdapterItemBinding
import chat.revolt.dashboard.presentation.chat_fragment.di.chatModule
import chat.revolt.dashboard.presentation.chat_fragment.vm.ChatViewModel
import chat.revolt.domain.models.Message
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module
import java.text.SimpleDateFormat
import java.util.*

class ChatFragment :
    BaseFragment<ChatViewModel, ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    private lateinit var messageCollectorJob: Job
    override val viewModel: ChatViewModel by viewModel()
    override val module: List<Module>
        get() = listOf(chatModule)

    object Comparator : DiffUtil.ItemCallback<Message>() {
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
                if (!viewModel.isLoading || initial)
                    binding.chatRecyclerView.scrollToPosition(adapter.itemCount - 1)
                initial = false
                viewModel.isLoading = false
            }
        })
        val lm = LinearLayoutManager(context)

        binding.chatRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            viewModel.changeChannel("01FVDG79NRQCS9MRJSVTDYHYPV")
        }
        viewModel.typers.observe {
            binding.typers.text = it
            binding.typers.visibility = if (it != null) View.VISIBLE else View.GONE
        }
        binding.plus.setOnClickListener {
            viewModel.changeChannel(channelId = if (viewModel.currentChannel.value == "01FVDG79NRQCS9MRJSVTDYHYPV") "01FVSDRN16DHQ8FANQS3TKS1WF" else "01FVDG79NRQCS9MRJSVTDYHYPV")
        }
        viewModel.currentChannel.observe {
            viewModel.load()
            if (this@ChatFragment::messageCollectorJob.isInitialized) {
                messageCollectorJob.cancel()
            }
            startMessageCollector(lifecycleScope, adapter)
        }
    }

    private fun startMessageCollector(
        coroutineScope: CoroutineScope,
        adapter: PagedListDelegationAdapter<Message>
    ) {
        messageCollectorJob = coroutineScope.launch {
            viewModel.flow.cancellable().collect {
                adapter.submitList(it)
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
                binding.date.text = UlidTimeDecoder.getTimestamp(item.id).toDate(requireContext())
                Glide.with(context).load(item.author.avatarUrl).into(binding.authorImage)
            }
        }
}