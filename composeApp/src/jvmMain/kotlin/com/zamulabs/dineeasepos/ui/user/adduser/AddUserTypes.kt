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

import androidx.compose.runtime.Immutable

@Immutable
data class AddUserUiState(
    val name: String = "",
    val email: String = "",
    val role: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isActive: Boolean = true,
    val roles: List<String> = listOf("Select role", "Admin", "Manager", "Waiter", "Chef"),
)

sealed interface AddUserUiEvent {
    data class OnNameChanged(
        val value: String,
    ) : AddUserUiEvent

    data class OnEmailChanged(
        val value: String,
    ) : AddUserUiEvent

    data class OnRoleChanged(
        val value: String,
    ) : AddUserUiEvent

    data class OnPasswordChanged(
        val value: String,
    ) : AddUserUiEvent

    data class OnConfirmPasswordChanged(
        val value: String,
    ) : AddUserUiEvent

    data class OnActiveChanged(
        val value: Boolean,
    ) : AddUserUiEvent

    data object OnCancel : AddUserUiEvent

    data object OnSave : AddUserUiEvent
}
