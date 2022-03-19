/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:54 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:54 PM
 */

package chat.revolt.dashboard.presentation.servers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.dashboard.databinding.ServerItemBinding
import chat.revolt.domain.models.server.Server
import com.bumptech.glide.Glide

class ServerViewHolder(parent: ViewGroup, private val onClick: (serverId: String) -> Unit) : BaseServerViewHolder<ServerItemBinding>(
    ServerItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) {

    private var serverId: String? = null

    init {
        binding.root.setOnClickListener { serverId?.let { id -> onClick.invoke(id) } }
    }

    override fun onBind(server: Server) {
        serverId = server.id
        Glide.with(context).load(server.icon?.url).into(binding.serverImage)
    }
}
