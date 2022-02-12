/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 9:34 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 9:34 PM
 */

package chat.revolt.auth.data.captcha_manager

import android.content.Context
import chat.revolt.auth.domain.captcha_manager.CaptchaListener
import chat.revolt.auth.domain.captcha_manager.CaptchaManager
import chat.revolt.core.server_config.RevoltConfigManager
import com.hcaptcha.sdk.HCaptcha
import java.lang.ref.WeakReference

class CaptchaManagerImpl(private val revoltConfigManager: RevoltConfigManager): CaptchaManager {

    private var listener: CaptchaListener? = null

    override fun solveCaptcha(contextRef: WeakReference<Context>) {
        val siteKey = revoltConfigManager.getConfig().features.captcha.siteKey
        contextRef.get()?.let { context ->
            HCaptcha.getClient(context).verifyWithHCaptcha(siteKey)
                .addOnSuccessListener { listener?.onSuccess(it.tokenResult) }
                .addOnFailureListener { listener?.onFail(it.localizedMessage) }
        };
    }

    override fun setListener(listener: CaptchaListener) {
        this.listener = listener
    }

    override fun removeListener() {
        listener = null
    }
}