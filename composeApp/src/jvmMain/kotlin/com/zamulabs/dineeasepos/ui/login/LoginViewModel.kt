/*
 * Copyright 2025 Zamulabs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zamulabs.dineeasepos.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: com.zamulabs.dineeasepos.data.DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEffect = kotlinx.coroutines.channels.Channel<LoginUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun updateUiState(block: LoginUiState.() -> LoginUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnEmailChanged -> updateUiState { copy(email = event.value) }
            is LoginUiEvent.OnPasswordChanged -> updateUiState { copy(password = event.value) }
            is LoginUiEvent.OnResetCodeChanged -> updateUiState { copy(resetCode = event.value) }
            is LoginUiEvent.OnNewPasswordChanged -> updateUiState { copy(newPassword = event.value) }
            is LoginUiEvent.OnConfirmPasswordChanged -> updateUiState { copy(confirmPassword = event.value) }

            LoginUiEvent.OnForgotPassword -> updateUiState { copy(showForgotPassword = true) }
            LoginUiEvent.OnDismissForgot -> updateUiState { copy(showForgotPassword = false) }
            LoginUiEvent.OnShowReset -> updateUiState { copy(showResetPassword = true) }
            LoginUiEvent.OnDismissReset -> updateUiState { copy(showResetPassword = false) }

            LoginUiEvent.OnSubmit -> submitLogin()
            LoginUiEvent.OnSubmitForgot -> submitForgot()
            LoginUiEvent.OnSubmitReset -> submitReset()

            LoginUiEvent.OnSignup -> { /* future: navigate to signup */ }
        }
    }

    private fun submitLogin() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password
        if (email.isBlank() || password.isBlank()) {
            viewModelScope.launch { _uiEffect.send(LoginUiEffect.ShowSnackBar("Please enter email and password")) }
            return
        }
        updateUiState { copy(loading = true, error = null) }
        viewModelScope.launch {
            when (val result = repository.login(email, password)) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    updateUiState { copy(loading = false, error = result.errorMessage) }
                    _uiEffect.send(LoginUiEffect.ShowSnackBar(result.errorMessage ?: "Login failed"))
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    updateUiState { copy(loading = false, error = null) }
                    _uiEffect.send(LoginUiEffect.NavigateToDashboard)
                }
            }
        }
    }

    private fun submitForgot() {
        val email = _uiState.value.email.trim()
        if (email.isBlank()) {
            viewModelScope.launch { _uiEffect.send(LoginUiEffect.ShowSnackBar("Enter your email first")) }
            return
        }
        updateUiState { copy(loading = true) }
        viewModelScope.launch {
            when (val result = repository.forgotPassword(email)) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    updateUiState { copy(loading = false) }
                    _uiEffect.send(LoginUiEffect.ShowSnackBar(result.errorMessage ?: "Failed to send reset email"))
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    updateUiState { copy(loading = false, showForgotPassword = false, showResetPassword = true) }
                    _uiEffect.send(LoginUiEffect.ShowSnackBar(result.data ?: "Reset code sent to your email"))
                }
            }
        }
    }

    private fun submitReset() {
        val state = _uiState.value
        if (state.email.isBlank() || state.resetCode.isBlank() || state.newPassword.isBlank()) {
            viewModelScope.launch { _uiEffect.send(LoginUiEffect.ShowSnackBar("Fill all fields")) }
            return
        }
        if (state.newPassword != state.confirmPassword) {
            viewModelScope.launch { _uiEffect.send(LoginUiEffect.ShowSnackBar("Passwords do not match")) }
            return
        }
        updateUiState { copy(loading = true) }
        viewModelScope.launch {
            when (val result = repository.resetPassword(state.email.trim(), state.resetCode, state.newPassword)) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    updateUiState { copy(loading = false) }
                    _uiEffect.send(LoginUiEffect.ShowSnackBar(result.errorMessage ?: "Failed to reset password"))
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    updateUiState { copy(loading = false, showResetPassword = false, password = "", newPassword = "", confirmPassword = "", resetCode = "") }
                    _uiEffect.send(LoginUiEffect.ShowSnackBar(result.data ?: "Password reset successful. Please login."))
                }
            }
        }
    }
}
