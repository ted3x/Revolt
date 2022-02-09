/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 9:43 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 9:43 PM
 */

package chat.revolt.auth.domain.captcha_manager

interface CaptchaListener {
    fun onSuccess(captcha: String)
    fun onFail(error: String?)
}