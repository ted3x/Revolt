/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 2:26 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 2:07 AM
 */

package chat.revolt.auth.navigator

import chat.revolt.auth.presentation.sign_in.ui.SignInFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object AuthScreens {
    fun SignIn() = FragmentScreen { SignInFragment() }
}