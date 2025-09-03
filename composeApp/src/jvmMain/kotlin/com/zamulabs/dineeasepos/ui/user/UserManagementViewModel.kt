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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserManagementViewModel : ViewModel() {
    var uiState by mutableStateOf(
        UserManagementUiState(
            users =
                listOf(
                    User("Sophia Bennett", "Manager", true),
                    User("Ethan Carter", "Server", true),
                    User("Olivia Davis", "Chef", true),
                    User("Liam Foster", "Bartender", false),
                    User("Ava Green", "Hostess", true),
                ),
        ),
    )
        private set

    fun onEvent(event: UserManagementUiEvent) {
        when (event) {
            is UserManagementUiEvent.OnClickAddUser -> { /* route from Screen */ }
            is UserManagementUiEvent.OnEdit -> { /* route from Screen */ }
            is UserManagementUiEvent.OnToggleActive -> {
                val list = uiState.users.toMutableList()
                val u = list[event.index]
                list[event.index] = u.copy(active = !u.active)
                uiState = uiState.copy(users = list)
            }
        }
    }
}
