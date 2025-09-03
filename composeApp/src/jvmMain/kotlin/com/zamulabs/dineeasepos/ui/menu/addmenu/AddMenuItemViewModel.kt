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

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class AddMenuItemViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddMenuItemUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<AddMenuItemUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun update(block: AddMenuItemUiState.() -> AddMenuItemUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: AddMenuItemUiEvent) {
        when (event) {
            is AddMenuItemUiEvent.OnNameChanged -> update { copy(name = event.value) }
            is AddMenuItemUiEvent.OnDescriptionChanged -> update { copy(description = event.value) }
            is AddMenuItemUiEvent.OnPriceChanged -> update { copy(price = event.value.filter { it.isDigit() || it == '.' }.take(10)) }
            is AddMenuItemUiEvent.OnCategoryChanged -> update { copy(category = event.value) }
            is AddMenuItemUiEvent.OnStatusChanged -> update { copy(status = event.value) }
            is AddMenuItemUiEvent.OnPrepTimeChanged -> update { copy(prepTimeMinutes = event.value.filter { it.isDigit() }.take(3)) }
            is AddMenuItemUiEvent.OnIngredientsChanged -> update { copy(ingredients = event.value) }
            AddMenuItemUiEvent.OnCancel -> _uiEffect.trySend(AddMenuItemUiEffect.NavigateBack)
            AddMenuItemUiEvent.OnSave -> _uiEffect.trySend(AddMenuItemUiEffect.ShowToast("Saved"))
        }
    }
}
