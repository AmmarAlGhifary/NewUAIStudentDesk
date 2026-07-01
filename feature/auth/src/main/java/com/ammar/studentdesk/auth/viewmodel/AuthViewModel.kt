package com.ammar.studentdesk.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ammar.studentdesk.auth.data.AuthRepository
import com.ammar.studentdesk.auth.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(nim: String, password: String){
        if (nim.isBlank() || password.isEmpty()) {
            _uiState.value = AuthUiState.Error("NIM and Password cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            val result = authRepository.login(nim, password)
            result.onSuccess { response ->
                _uiState.value = AuthUiState.Success(response.token)
            }.onFailure { error ->
                _uiState.value = AuthUiState.Error(error.message ?: "An unkown error ocurred")
            }
        }
    }
}