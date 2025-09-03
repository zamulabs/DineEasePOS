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

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val name: String,
    val role: String,
    val active: Boolean,
)

@Immutable
data class UserManagementUiState(
    val users: List<User> = emptyList(),
)

sealed interface UserManagementUiEvent {
    data object OnClickAddUser : UserManagementUiEvent

    data class OnToggleActive(
        val index: Int,
    ) : UserManagementUiEvent

    data class OnEdit(
        val index: Int,
    ) : UserManagementUiEvent
}
