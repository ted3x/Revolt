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

    var isEndReached = false
    protected var isLoaderVisible = false
    abstract val emptyItem:T
    abstract val loadOffset: Int

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): VH
    abstract fun getViewType(position: Int): Int
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                if (lastVisibleItem + loadOffset >= itemCount && !isEndReached && !isLoaderVisible) {
                    toggleLoader(true)
                    listener.onLoadMore()
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == currentList.lastIndex && isLoaderVisible) LOADING_TYPE
        else getViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if(viewType == LOADING_TYPE) LoadingViewHolder(LoadingAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)) as VH
        else getViewHolder(parent, viewType)
    }

    fun setList(list: List<T>) {
        if(isLoaderVisible) toggleLoader(false)
        submitList(list)
    }

    private fun toggleLoader(show: Boolean) {
        isLoaderVisible = show
        val newList = currentList.toMutableList()
        if(show) {
            newList.add(emptyItem)
        }
        else {
            newList.removeAt(newList.size - 1)
        }
        submitList(newList)
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