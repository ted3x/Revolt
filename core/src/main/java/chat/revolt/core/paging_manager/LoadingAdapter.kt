/*
 * Created by Tedo Manvelidze(ted3x) on 3/8/22, 11:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/8/22, 11:24 PM
 */

package chat.revolt.core.paging_manager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.core.databinding.LoadingAdapterItemBinding

abstract class LoadingAdapter<T, VH : LoadingAdapter.BaseLoadingAdapterViewHolder>(
    diffUtil: DiffUtil.ItemCallback<T>,
    private val listener: LoadingAdapterListener
) :
    ListAdapter<T,VH>(diffUtil) {

    protected var isLoaderVisible = false
    abstract val positionToLoad: Int

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): VH
    abstract fun getViewType(position: Int): Int
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                if (lastVisibleItem + positionToLoad >= itemCount) {
                    isLoaderVisible = true
                    notifyItemInserted(itemCount)
                    listener.onLoadMore()
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == itemCount - 1 && isLoaderVisible) LOADING_TYPE
        else getViewType(position)
    }

    override fun submitList(list: List<T>?) {
        if(isLoaderVisible) notifyItemRemoved(itemCount - 1)
        isLoaderVisible = false
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if(viewType == LOADING_TYPE) LoadingViewHolder(LoadingAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)) as VH
        else getViewHolder(parent, viewType)
    }

    class LoadingViewHolder(binding: LoadingAdapterItemBinding) : BaseLoadingAdapterViewHolder(binding.root)
    abstract class BaseLoadingAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        private const val LOADING_TYPE = 3883714
    }
}

interface LoadingAdapterListener {
    fun onLoadMore()
}