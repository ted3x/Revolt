/*
 * Created by Tedo Manvelidze(ted3x) on 3/11/22, 11:46 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/11/22, 11:46 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.adapter.view_holders

import androidx.viewbinding.ViewBinding
import chat.revolt.core.paging_manager.LoadingAdapter
import chat.revolt.domain.models.Message

abstract class MessageViewHolder<VB: ViewBinding>(protected val binding: VB) : LoadingAdapter.BaseLoadingAdapterViewHolder(binding.root) {

    abstract fun onBind(item: Message)
}