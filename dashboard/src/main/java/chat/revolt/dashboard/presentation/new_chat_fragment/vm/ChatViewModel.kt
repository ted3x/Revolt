/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 2:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 2:53 PM
 */

package chat.revolt.dashboard.presentation.new_chat_fragment.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import chat.revolt.core.view_model.BaseViewModel
import chat.revolt.dashboard.domain.repository.ChannelRepository

class NewChatViewModel(
    private val repository: ChannelRepository,
) : BaseViewModel() {

    val flow = repository.getMessages("01FW9XW0MCK2CXPRAA7AAY59CB").cachedIn(viewModelScope)
}