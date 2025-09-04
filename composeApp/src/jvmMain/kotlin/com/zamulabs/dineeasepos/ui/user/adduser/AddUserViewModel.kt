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
package com.zamulabs.dineeasepos.ui.user.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamulabs.dineeasepos.data.DineEaseRepository
import com.zamulabs.dineeasepos.utils.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddUserViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val settings: com.zamulabs.dineeasepos.data.SettingsRepository = org.koin.java.KoinJavaComponent.get(com.zamulabs.dineeasepos.data.SettingsRepository::class.java)
    private val _uiState = MutableStateFlow(AddUserUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<AddUserUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun update(block: AddUserUiState.() -> AddUserUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: AddUserUiEvent) {
        when (event) {
            is AddUserUiEvent.OnNameChanged -> update { copy(name = event.value) }
            is AddUserUiEvent.OnEmailChanged -> update { copy(email = event.value) }
            is AddUserUiEvent.OnRoleChanged -> update { copy(role = event.value) }
            is AddUserUiEvent.OnPasswordChanged -> update { copy(password = event.value) }
            is AddUserUiEvent.OnConfirmPasswordChanged -> update { copy(confirmPassword = event.value) }
            is AddUserUiEvent.OnActiveChanged -> update { copy(isActive = event.value) }
            AddUserUiEvent.OnCancel -> _uiEffect.trySend(AddUserUiEffect.NavigateBack)
            AddUserUiEvent.OnSave -> saveUser()
        }
    }

    private fun saveUser() {
        val s = uiState.value
        if (s.name.isBlank() || s.email.isBlank() || s.role.isBlank()) {
            _uiEffect.trySend(AddUserUiEffect.ShowSnackBar("Please fill all required fields"))
            return
        }
        viewModelScope.launch {
            when (val result = repository.createUserAdmin(
                name = s.name,
                email = s.email,
                role = s.role,
            )) {
                is NetworkResult.Error -> {
                    _uiEffect.trySend(AddUserUiEffect.ShowSnackBar(result.errorMessage ?: "Failed to add user"))
                }
                is NetworkResult.Success -> {
                    val temp = result.data ?: "Temp password generated"
                    // Mark this email as requiring first-login password update
                    settings.setFirstLoginEmail(s.email)
                    // Show credentials once to admin
                    _uiEffect.trySend(AddUserUiEffect.ShowCredentials(email = s.email, tempPassword = temp))
                }
            }
        }
    }
}
