package com.ammar.studentdesk.screenhost.state

import com.ammar.studentdesk.sdui.domain.model.SduiScreen

sealed class ScreenHostUiState {
    object Loading : ScreenHostUiState()
    data class Success(val screen: SduiScreen) : ScreenHostUiState()
    data class Error(val message: String) : ScreenHostUiState()
}