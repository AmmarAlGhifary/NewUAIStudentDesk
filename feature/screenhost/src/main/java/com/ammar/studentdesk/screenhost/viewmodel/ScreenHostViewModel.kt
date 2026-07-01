package com.ammar.studentdesk.screenhost.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ammar.studentdesk.screenhost.state.ScreenHostUiState
import com.ammar.studentdesk.sdui.domain.usecase.GetSduiScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenHostViewModel @Inject constructor(
    private val getSduiScreenUseCase: GetSduiScreenUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScreenHostUiState>(ScreenHostUiState.Loading)
    val uiState: StateFlow<ScreenHostUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing : StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var currentScreenId: String = "home"

    fun fetchScreen(screenId: String) {
        if (_uiState.value is ScreenHostUiState.Success && currentScreenId == screenId) return
        currentScreenId = screenId
        viewModelScope.launch {
            _uiState.value = ScreenHostUiState.Loading
            getSduiScreenUseCase(screenId).collect { result ->
                result.fold(
                    onSuccess = { sduiScreen ->
                        _uiState.value = ScreenHostUiState.Success(sduiScreen)
                    },
                    onFailure = { error ->
                        _uiState.value = ScreenHostUiState.Error(error.message ?: "An unknown error occurred")
                    }
                )
            }
        }
    }

    fun refreshScreen() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                getSduiScreenUseCase(currentScreenId).collect { result ->
                    _uiState.value = ScreenHostUiState.Success(result.getOrThrow())
                    _isRefreshing.value = false
                }
            } catch (e: Exception){
                _uiState.value = ScreenHostUiState.Error(e.message ?: "Unknown error")
                _isRefreshing.value = false
            }
        }
    }
}


