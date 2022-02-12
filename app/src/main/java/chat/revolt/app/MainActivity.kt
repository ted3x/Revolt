/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:09 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:00 AM
 */

package chat.revolt.app

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import chat.revolt.core.BaseActivity
import chat.revolt.core_navigation.features.Feature
import chat.revolt.core_navigation.features.auth.AuthStates
import chat.revolt.core_navigation.navigator.GlobalNavigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity() {
    private val navigatorHolder by inject<NavigatorHolder>()
    private val appNavigator by inject<AppNavigator> { parametersOf(this, R.id.container) }
    private val globalNavigator by inject<GlobalNavigator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        globalNavigator.navigateTo(Feature.Auth(state = AuthStates.SignIn))
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(appNavigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

   override fun showLoading() {
        val loadingView = findViewById<ViewStub>(R.id.loadingViewStub).inflate()
        val container = findViewById<FrameLayout>(R.id.container)
        container.alpha = 0.8f
        loadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        val loadingView = findViewById<FrameLayout>(R.id.loadingView)
        val container = findViewById<FrameLayout>(R.id.container)
        container.alpha = 1f
        loadingView.visibility = View.GONE
    }
}