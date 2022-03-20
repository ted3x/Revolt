/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 4:28 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 4:28 PM
 */

package chat.revolt.dashboard.presentation.servers.adapter.channels

import android.view.LayoutInflater
import android.view.ViewGroup
import chat.revolt.dashboard.databinding.CategoryItemBinding
import chat.revolt.dashboard.presentation.servers.adapter.ChannelUiItem

class CategoryViewHolder(
    parent: ViewGroup,
    onCategoryVisibilityChange: (categoryId: String) -> Unit
) :
    BaseChannelViewHolder<CategoryItemBinding, ChannelUiItem.Category>(
        CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) {
    private var category: ChannelUiItem.Category? = null

    init {
        binding.root.setOnClickListener {
            if(category == null) return@setOnClickListener
            else {
                onCategoryVisibilityChange.invoke(category!!.id)
            }
        }
    }

    override fun onBind(item: ChannelUiItem.Category) {
        category = item
        binding.categoryName.text = item.name
        binding.categoryIcon.rotation = if (item.isVisible) 0f else 270f
    }

}