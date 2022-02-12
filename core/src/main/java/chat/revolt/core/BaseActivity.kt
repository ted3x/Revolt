/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 7:07 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 7:07 PM
 */

package chat.revolt.core

import org.koin.androidx.scope.ScopeActivity

abstract class BaseActivity : ScopeActivity() {

    abstract fun showLoading()

    abstract fun hideLoading()
}