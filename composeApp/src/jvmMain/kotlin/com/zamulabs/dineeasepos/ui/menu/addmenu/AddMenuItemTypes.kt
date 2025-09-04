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
package com.zamulabs.dineeasepos.ui.menu.addmenu

import androidx.compose.runtime.Immutable

@Immutable
data class AddMenuItemUiState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val status: String = "",
    val prepTimeMinutes: String = "",
    val ingredients: String = "",
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val categories: List<String> = listOf("Select Category", "Starters", "Mains", "Desserts", "Drinks"),
    val statuses: List<String> = listOf("Select Status", "Active", "Inactive"),
    val snackbarHostState: androidx.compose.material3.SnackbarHostState = androidx.compose.material3.SnackbarHostState(),
    val isEdit: Boolean = false,
    val originalName: String? = null,
)

sealed interface AddMenuItemUiEvent {
    data class OnNameChanged(
        val value: String,
    ) : AddMenuItemUiEvent

    data class OnDescriptionChanged(
        val value: String,
    ) : AddMenuItemUiEvent

    data class OnPriceChanged(
        val value: String,
    ) : AddMenuItemUiEvent

    data class OnCategoryChanged(
        val value: String,
    ) : AddMenuItemUiEvent

    data class OnStatusChanged(
        val value: String,
    ) : AddMenuItemUiEvent

    data class OnPrepTimeChanged(
        val value: String,
    ) : AddMenuItemUiEvent

    data class OnIngredientsChanged(
        val value: String,
    ) : AddMenuItemUiEvent

    data object OnCancel : AddMenuItemUiEvent

    data object OnSave : AddMenuItemUiEvent
}

sealed class AddMenuItemUiEffect {
    data class ShowToast(val message: String) : AddMenuItemUiEffect()
    data class ShowSnackBar(val message: String) : AddMenuItemUiEffect()
    data object NavigateBack : AddMenuItemUiEffect()
}
