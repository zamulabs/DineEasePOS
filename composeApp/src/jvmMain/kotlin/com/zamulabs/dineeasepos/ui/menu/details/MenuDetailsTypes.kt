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
package com.zamulabs.dineeasepos.ui.menu.details

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable

@Immutable
data class MenuDetailsUiState(
    val id: String = "#MI-001",
    val name: String = "Classic Burger",
    val category: String = "Mains",
    val price: String = "$12.00",
    val active: Boolean = true,
    val description: String = "Juicy beef patty with lettuce, tomato, and house sauce.",
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)

sealed interface MenuDetailsUiEvent {
    data object Edit : MenuDetailsUiEvent
    data object ToggleActive : MenuDetailsUiEvent
    data object Delete : MenuDetailsUiEvent
}

sealed class MenuDetailsUiEffect {
    data class ShowToast(val message: String) : MenuDetailsUiEffect()
    data class ShowSnackBar(val message: String) : MenuDetailsUiEffect()
    data object NavigateBack : MenuDetailsUiEffect()
    data object NavigateToEdit : MenuDetailsUiEffect()
}
