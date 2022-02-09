/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 11:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 11:51 PM
 */

package chat.revolt.core.extensions

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.onChange(callback: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            callback.invoke(p0.toString())
        }
    })
}