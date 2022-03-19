/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 5:03 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 5:03 PM
 */

package chat.revolt.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide.with(this.context).load(url).into(this)
}