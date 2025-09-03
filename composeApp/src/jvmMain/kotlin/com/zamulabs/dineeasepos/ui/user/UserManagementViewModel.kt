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
package com.zamulabs.dineeasepos.ui.user

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

class UserManagementViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserManagementUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<UserManagementUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun updateUiState(block: UserManagementUiState.() -> UserManagementUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: UserManagementUiEvent) {
        when (event) {
            is UserManagementUiEvent.OnClickAddUser -> { /* Navigation handled in Screen */ }
            is UserManagementUiEvent.OnEdit -> { /* Navigation handled in Screen */ }
            is UserManagementUiEvent.OnToggleActive -> {
                updateUiState {
                    val list = users.toMutableList()
                    val u = list[event.index]
                    list[event.index] = u.copy(active = !u.active)
                    copy(users = list)
                }
            }
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            when (val result = repository.getUsers()) {
                is NetworkResult.Error -> {
                    updateUiState {
                        copy(
                            users = sampleUsers(),
                        )
                    }
                }
                is NetworkResult.Success -> {
                    updateUiState {
                        copy(
                            users = result.data ?: sampleUsers(),
                        )
                    }
                }
            }
        }
    }

    private fun sampleUsers(): List<User> = listOf(
        User("Sophia Bennett", "Manager", true),
        User("Ethan Carter", "Server", true),
        User("Olivia Davis", "Chef", true),
        User("Liam Foster", "Bartender", false),
        User("Ava Green", "Hostess", true),
    )
}
