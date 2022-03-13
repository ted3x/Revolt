/*
 * Created by Tedo Manvelidze(ted3x) on 3/11/22, 11:16 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/11/22, 11:16 PM
 */

package chat.revolt.core.extensions

import android.view.View

fun View.visibleIf(value: Boolean) {
    this.visibility = if (value) View.VISIBLE else View.GONE
}

fun View.visibleIf(block: () -> Boolean) {
    this.visibility = if (block.invoke()) View.VISIBLE else View.GONE
}

fun View.hiddenIf(block: () -> Boolean) {
    this.visibility = if (block.invoke()) View.INVISIBLE else View.VISIBLE
}