/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 12:47 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 12:47 AM
 */

package chat.revolt.dashboard.presentation.servers.adapter.channels

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import chat.revolt.dashboard.presentation.servers.adapter.ChannelUiItem

abstract class BaseChannelViewHolder<VB: ViewBinding, T: ChannelUiItem>(protected val binding: VB): RecyclerView.ViewHolder(binding.root) {

    val context: Context = binding.root.context
    abstract fun onBind(item: T)
}