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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddUserViewModel : ViewModel() {
    var state by mutableStateOf(AddUserUiState())
        private set

    fun onEvent(event: AddUserUiEvent) {
        when (event) {
            is AddUserUiEvent.OnNameChanged -> state = state.copy(name = event.value)
            is AddUserUiEvent.OnEmailChanged -> state = state.copy(email = event.value)
            is AddUserUiEvent.OnRoleChanged -> state = state.copy(role = event.value)
            is AddUserUiEvent.OnPasswordChanged -> state = state.copy(password = event.value)
            is AddUserUiEvent.OnConfirmPasswordChanged -> state = state.copy(confirmPassword = event.value)
            is AddUserUiEvent.OnActiveChanged -> state = state.copy(isActive = event.value)
            AddUserUiEvent.OnCancel -> { /* no-op */ }
            AddUserUiEvent.OnSave -> { /* TODO persist */ }
        }
    }
}
