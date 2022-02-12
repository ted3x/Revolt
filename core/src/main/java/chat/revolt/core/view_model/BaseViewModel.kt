/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:33 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:33 AM
 */

package chat.revolt.core.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chat.revolt.core.loading_manager.LoadingManager
import org.koin.java.KoinJavaComponent.inject

abstract class BaseViewModel : ViewModel() {
    val snackBarMessage = MutableLiveData<String>()
    protected val loadingManager by inject<LoadingManager>(LoadingManager::class.java)
}