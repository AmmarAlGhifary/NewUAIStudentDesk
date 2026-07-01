package com.ammar.studentdesk.auth.state

sealed class AuthUiState {
    object Idle: AuthUiState()
    object Loading: AuthUiState()
    data class Error(val message: String): AuthUiState()
    data class Success(val token: String) : AuthUiState()
}