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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnEmailChanged -> _uiState.update { it.copy(email = event.value) }
            is LoginUiEvent.OnPasswordChanged -> _uiState.update { it.copy(password = event.value) }
            LoginUiEvent.OnForgotPassword -> { /* no-op for now */ }
            LoginUiEvent.OnSignup -> { /* no-op for now */ }
            LoginUiEvent.OnSubmit -> {
                _uiState.update { it.copy(loading = true, error = null) }
                // Fake success after simple validation
                val valid = _uiState.value.email.isNotBlank() && _uiState.value.password.isNotBlank()
                if (!valid) {
                    _uiState.update { it.copy(loading = false, error = "Please enter email and password") }
                } else {
                    _uiState.update { it.copy(loading = false, error = null) }
                }
            }
        }
    }
}
