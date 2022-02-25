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
import chat.revolt.core.extensions.toHour
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.dashboard.databinding.ChatFragmentBinding
import chat.revolt.dashboard.databinding.SystemTextAdapterItemBinding
import chat.revolt.dashboard.databinding.TextAdapterItemBinding
import chat.revolt.dashboard.presentation.chat_fragment.LoadingType
import chat.revolt.dashboard.presentation.chat_fragment.di.chatModule
import chat.revolt.dashboard.presentation.chat_fragment.vm.ChatViewModel
import chat.revolt.domain.models.Message
import chat.revolt.styles.R
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
            textAdapterItem(),
            systemMessageAdapterItem()
        )
        var isOnTop = false
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (!isOnTop && viewModel.loadingState.value == LoadingType.Initial)
                    binding.chatRecyclerView.scrollToPosition(0)
                lifecycleScope.launch { viewModel.loadingState.emit(LoadingType.NotLoading) }
            }
        })
        val lm = LinearLayoutManager(context)
        lm.reverseLayout = true
        lm.stackFromEnd = true

        binding.chatRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentFirstVisible: Int = lm.findLastCompletelyVisibleItemPosition()
                if (currentFirstVisible > recyclerView.adapter?.itemCount?.minus(5) ?: -1 && viewModel.loadingState.value == LoadingType.NotLoading && dy < 0) {
                    isOnTop = true
                    viewModel.load()
                }
                else {
                    isOnTop = false
                }
            }
        })
        binding.chatRecyclerView.layoutManager = lm
        binding.chatRecyclerView.adapter = adapter
        lifecycleScope.launchWhenResumed {
            viewModel.changeChannel("01F7ZSBSFHCAAJQ92ZGTY67HMN")
            launch {
                viewModel.loadingState.collect {
                    when (it) {
                        LoadingType.NotLoading, LoadingType.Initial -> binding.loading.root.visibility =
                            View.GONE
                        LoadingType.OnScroll -> binding.loading.root.visibility = View.VISIBLE
                    }
                }
            }
        }
        viewModel.typers.observe {
            binding.typers.text = it
            binding.typers.visibility = if (it != null) View.VISIBLE else View.GONE
        }
        binding.plus.setOnClickListener {
            viewModel.changeChannel(channelId = if (viewModel.currentChannel.value == "01FVDG79NRQCS9MRJSVTDYHYPV") "01FVSDRN16DHQ8FANQS3TKS1WF" else "01FVDG79NRQCS9MRJSVTDYHYPV")
        }
        viewModel.currentChannel.observe {
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
            viewModel.messages.collect(adapter::submitList)
            if(viewModel.isPaginationEndReached)
                viewModel.loadingState.emit(LoadingType.NotLoading)
        }
    }

    private fun textAdapterItem() =
        adapterDelegateViewBinding(on = { item: Message, _: List<Message>, _: Int ->
            item.content is Message.Content.Text || item.content is Message.Content.Message
        },
            viewBinding = { layoutInflater, root ->
                TextAdapterItemBinding.inflate(
                    layoutInflater,
                    root,
                    false
                )
            }
        ) {
            bind {
                binding.authorName.text = item.authorName
                binding.text.text = (item.content as? Message.Content.Message)?.content
                    ?: (item.content as? Message.Content.Text)?.content
                binding.date.text = UlidTimeDecoder.getTimestamp(item.id).toDate(requireContext())
                Glide.with(context).load(item.author.avatarUrl).into(binding.authorImage)
            }
        }

    private fun systemMessageAdapterItem() =
        adapterDelegateViewBinding(on = { item: Message, _: List<Message>, _: Int ->
            item.content is Message.SystemMessage
        },
            viewBinding = { layoutInflater, root ->
                SystemTextAdapterItemBinding.inflate(
                    layoutInflater,
                    root,
                    false
                )
            }
        ) {
            bind {
                binding.root.setOnClickListener {
                    binding.messageDate.visibility =
                        if (binding.messageDate.visibility == View.VISIBLE) View.INVISIBLE
                        else View.VISIBLE
                }
                val iconRes = item.content.getSystemMessageIconRes()
                binding.systemMessageIcon.setImageDrawable(getDrawable(iconRes))
                binding.message.text = (item.content as? Message.SystemMessage)?.message
                binding.messageDate.text = UlidTimeDecoder.getTimestamp(item.id).toHour()
                Glide.with(context).load((item.content as? Message.SystemMessage)?.authorImageUrl)
                    .into(binding.authorImage)
            }
        }
}

fun Message.Content.getSystemMessageIconRes(): Int {
    return when (this) {
        is Message.Content.ChannelDescriptionChanged, is Message.Content.ChannelIconChanged, is Message.Content.ChannelRenamed -> R.drawable.ic_channel_edit
        is Message.Content.UserAdded -> R.drawable.ic_user_add
        is Message.Content.UserBanned -> R.drawable.ic_user_blocked
        is Message.Content.UserJoined -> R.drawable.ic_user_joined
        is Message.Content.UserKicked -> R.drawable.ic_user_remove
        is Message.Content.UserLeft -> R.drawable.ic_user_left
        is Message.Content.UserRemove -> R.drawable.ic_user_remove
        else -> -1
    }
}