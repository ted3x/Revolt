/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 2:27 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 2:27 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.adapter.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.dashboard.R

class MessagesDecorator: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = (view.layoutParams as RecyclerView.LayoutParams).bindingAdapterPosition
        if(position == 0)
            outRect.bottom = view.context.resources.getDimensionPixelSize(R.dimen.dimen_p_30)
    }
}