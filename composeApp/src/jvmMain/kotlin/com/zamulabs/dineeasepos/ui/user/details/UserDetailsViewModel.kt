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
package com.zamulabs.dineeasepos.ui.user.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamulabs.dineeasepos.data.DineEaseRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<UserDetailsUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: UserDetailsUiState.() -> UserDetailsUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: UserDetailsUiEvent) {
        when (event) {
            UserDetailsUiEvent.Edit -> _uiEffect.trySend(UserDetailsUiEffect.NavigateToEdit)
            UserDetailsUiEvent.ToggleActive -> updateUiState { copy(active = !active) }
            UserDetailsUiEvent.ResetPassword -> viewModelScope.launch {
                _uiEffect.trySend(UserDetailsUiEffect.ShowSnackBar("Reset link sent to email"))
            }
        }
    }

    fun loadUserDetails(userId: String? = null) {
        viewModelScope.launch {
            val users = repository.getUsers().data.orEmpty()
            val user = if (userId != null) users.find { it.name == userId } else users.firstOrNull()
            user?.let { u ->
                updateUiState {
                    copy(
                        id = "#${u.name.hashCode()}",
                        name = u.name,
                        email = "",
                        role = u.role,
                        active = u.active,
                    )
                }
            }
        }
    }
}
