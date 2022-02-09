/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 9:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 9:51 PM
 */

package chat.revolt.auth.utils

import android.util.Patterns

fun String?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String?.isValidPassword() = !isNullOrEmpty() && this.length in 8..1024