/*
 * Created by Tedo Manvelidze(ted3x) on 2/26/22, 11:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/26/22, 11:53 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.dashboard.databinding.LoadingAdapterItemBinding

class LoadAdapter : LoadStateAdapter<LoadAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(private val binding: LoadingAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun onBind(loadState: LoadState) {
                binding.progressBar.visibility = if(loadState is LoadState.Loading) View.VISIBLE else View.GONE
            }
        }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(LoadingAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}