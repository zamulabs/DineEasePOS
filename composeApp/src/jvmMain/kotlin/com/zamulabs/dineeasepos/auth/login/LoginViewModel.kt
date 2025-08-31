package com.zamulabs.dineeasepos.auth.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginUiEvent){
        when(event){
            is LoginUiEvent.OnEmailChanged -> _uiState.update { it.copy(email = event.value) }
            is LoginUiEvent.OnPasswordChanged -> _uiState.update { it.copy(password = event.value) }
            LoginUiEvent.OnForgotPassword -> { /* no-op for now */ }
            LoginUiEvent.OnSignup -> { /* no-op for now */ }
            LoginUiEvent.OnSubmit -> {
                _uiState.update { it.copy(loading = true, error = null) }
                // Fake success after simple validation
                val valid = _uiState.value.email.isNotBlank() && _uiState.value.password.isNotBlank()
                if(!valid){
                    _uiState.update { it.copy(loading = false, error = "Please enter email and password") }
                } else {
                    _uiState.update { it.copy(loading = false, error = null) }
                }
            }
        }
    }
}
