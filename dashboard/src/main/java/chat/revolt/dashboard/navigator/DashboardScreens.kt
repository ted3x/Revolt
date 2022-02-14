/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:29 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:26 PM
 */

package chat.revolt.dashboard.navigator

import androidx.paging.ExperimentalPagingApi
import chat.revolt.dashboard.presentation.dashboard.ui.DashboardFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object DashboardScreens {

    @OptIn(ExperimentalPagingApi::class)
    fun Dashboard() = FragmentScreen { DashboardFragment() }
}