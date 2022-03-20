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
import android.R

import android.view.animation.LinearInterpolator

import android.view.animation.Animation

import android.view.animation.RotateAnimation


class CategoryViewHolder(
    parent: ViewGroup,
    onCategoryVisibilityChange: (categoryId: String) -> Unit
) :
    BaseChannelViewHolder<CategoryItemBinding, ChannelUiItem.Category>(
        CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) {
    private var category: ChannelUiItem.Category? = null
    private var isVisible: Boolean = false

    init {
        binding.root.setOnClickListener {
            if (category == null) return@setOnClickListener
            else {
                val rotate = if (isVisible) getRotationAnimation(
                    from = 0f,
                    to = 90f
                ) else getRotationAnimation(from = 90f, to = 0f)
                rotate.duration = 200
                rotate.interpolator = LinearInterpolator()
                rotate.fillAfter = true

                binding.categoryIcon.startAnimation(rotate)
                isVisible = !isVisible
                onCategoryVisibilityChange.invoke(category!!.id)
            }
        }
    }

    private fun getRotationAnimation(from: Float, to: Float): RotateAnimation {
        return RotateAnimation(
            from,
            to,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
    }

    override fun onBind(item: ChannelUiItem.Category) {
        category = item
        isVisible = item.isVisible
        binding.categoryName.text = item.name
        binding.categoryIcon.rotation = if (item.isVisible) 270f else 0f
    }

}