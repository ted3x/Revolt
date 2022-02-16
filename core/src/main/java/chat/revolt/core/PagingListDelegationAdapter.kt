/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 1:23 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 1:23 AM
 */

package chat.revolt.core

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class PagedListDelegationAdapter<T : Any>(private val diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    private val delegatesManager: AdapterDelegatesManager<List<T>> = AdapterDelegatesManager()

    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        vararg delegates: AdapterDelegate<List<T>>
    ) : this(diffCallback) {
        delegates.forEach {
            delegatesManager.addDelegate(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position) // Internally triggers loading items around items around the given position
        delegatesManager.onBindViewHolder(currentList, position, holder, null)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int,
        payloads: List<*>
    ) {
        getItem(position) // Internally triggers loading items around items around the given position
        delegatesManager.onBindViewHolder(currentList, position, holder, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(currentList, position)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return delegatesManager.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewDetachedFromWindow(holder)
    }
}
