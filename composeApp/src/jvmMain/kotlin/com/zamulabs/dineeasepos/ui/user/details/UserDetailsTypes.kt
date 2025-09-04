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

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable

@Immutable
data class UserDetailsUiState(
    val id: String = "#U-001",
    val name: String = "Sarah Chen",
    val email: String = "sarah.chen@example.com",
    val role: String = "Waiter",
    val active: Boolean = true,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)

sealed interface UserDetailsUiEvent {
    data object Edit : UserDetailsUiEvent
    data object ToggleActive : UserDetailsUiEvent
    data object ResetPassword : UserDetailsUiEvent
}

sealed class UserDetailsUiEffect {
    data class ShowToast(val message: String) : UserDetailsUiEffect()
    data class ShowSnackBar(val message: String) : UserDetailsUiEffect()
    data object NavigateBack : UserDetailsUiEffect()
    data object NavigateToEdit : UserDetailsUiEffect()
}
