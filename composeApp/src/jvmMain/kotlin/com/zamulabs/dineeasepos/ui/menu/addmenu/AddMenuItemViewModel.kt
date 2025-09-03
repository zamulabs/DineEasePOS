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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddMenuItemViewModel : ViewModel() {
    var uiState by mutableStateOf(AddMenuItemUiState())
        private set

    fun onEvent(event: AddMenuItemUiEvent) {
        when (event) {
            is AddMenuItemUiEvent.OnNameChanged -> uiState = uiState.copy(name = event.value)
            is AddMenuItemUiEvent.OnDescriptionChanged -> uiState = uiState.copy(description = event.value)
            is AddMenuItemUiEvent.OnPriceChanged ->
                uiState =
                    uiState.copy(
                        price = event.value.filter { it.isDigit() || it == '.' }.take(10),
                    )
            is AddMenuItemUiEvent.OnCategoryChanged -> uiState = uiState.copy(category = event.value)
            is AddMenuItemUiEvent.OnStatusChanged -> uiState = uiState.copy(status = event.value)
            is AddMenuItemUiEvent.OnPrepTimeChanged -> uiState = uiState.copy(prepTimeMinutes = event.value.filter { it.isDigit() }.take(3))
            is AddMenuItemUiEvent.OnIngredientsChanged -> uiState = uiState.copy(ingredients = event.value)
            AddMenuItemUiEvent.OnCancel -> {}
            AddMenuItemUiEvent.OnSave -> {}
        }
    }
}
