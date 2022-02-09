/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 9:34 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 9:32 PM
 */

package chat.revolt.auth.domain.captcha_manager

import android.content.Context
import java.lang.ref.WeakReference

interface CaptchaManager {
    fun solveCaptcha(contextRef: WeakReference<Context>)
    fun setListener(listener: CaptchaListener)
    fun removeListener()
}