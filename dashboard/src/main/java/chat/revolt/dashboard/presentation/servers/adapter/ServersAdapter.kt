/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:52 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:52 PM
 */

package chat.revolt.dashboard.presentation.servers.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.domain.models.server.Server

class ServersAdapter(private val onServerClick: (serverId: String) -> Unit): ListAdapter<Server, BaseServerViewHolder<*>>(COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseServerViewHolder<*> {
        return ServerViewHolder(parent, onServerClick)
    }

    override fun onBindViewHolder(holder: BaseServerViewHolder<*>, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val COMPARATOR = object: DiffUtil.ItemCallback<Server>() {
            override fun areItemsTheSame(oldItem: Server, newItem: Server): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Server, newItem: Server): Boolean {
                return oldItem.id == newItem.id && oldItem.icon == newItem.icon
            }
        }
    }
}