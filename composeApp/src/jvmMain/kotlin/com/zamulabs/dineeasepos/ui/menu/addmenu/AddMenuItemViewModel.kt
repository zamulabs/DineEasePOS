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
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddMenuItemViewModel(
    private val repository: com.zamulabs.dineeasepos.data.DineEaseRepository,
) : ViewModel() {
    fun setEditing(item: com.zamulabs.dineeasepos.ui.menu.MenuItem) {
        _uiState.update {
            it.copy(
                isEdit = true,
                originalName = item.name,
                name = item.name,
                description = "",
                price = item.price.filter { ch -> ch.isDigit() || ch == '.' },
                category = item.category,
                status = if (item.active) "Active" else "Inactive",
                prepTimeMinutes = "",
                ingredients = "",
            )
        }
    }
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
            AddMenuItemUiEvent.OnSave -> save()
        }
    }

    private fun save() {
        val state = _uiState.value
        val name = state.name.trim()
        val priceDouble = state.price.toDoubleOrNull()
        val category = state.category.takeIf { it.isNotBlank() && it != state.categories.first() }
        val active = when (state.status) {
            "Active" -> true
            "Inactive" -> false
            else -> null
        }

        if (name.isEmpty() || priceDouble == null || category == null || active == null) {
            _uiEffect.trySend(AddMenuItemUiEffect.ShowSnackBar("Please fill all required fields"))
            return
        }

        val prepMinutes = state.prepTimeMinutes.toIntOrNull()
        update { copy(isSaving = true, errorMessage = null) }

        viewModelScope.launch {
            val call = if (state.isEdit) {
                repository.updateMenuItem(
                    name = name,
                    description = state.description.ifBlank { null },
                    price = priceDouble,
                    category = category,
                    active = active,
                    prepTimeMinutes = prepMinutes,
                    ingredients = state.ingredients.ifBlank { null },
                )
            } else {
                repository.addMenuItem(
                    name = name,
                    description = state.description.ifBlank { null },
                    price = priceDouble,
                    category = category,
                    active = active,
                    prepTimeMinutes = prepMinutes,
                    ingredients = state.ingredients.ifBlank { null },
                )
            }
            when (val result = call) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    update { copy(isSaving = false) }
                    _uiEffect.trySend(AddMenuItemUiEffect.ShowSnackBar("Menu item saved"))
                    _uiEffect.trySend(AddMenuItemUiEffect.NavigateBack)
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    update { copy(isSaving = false, errorMessage = result.errorMessage) }
                    _uiEffect.trySend(AddMenuItemUiEffect.ShowSnackBar(result.errorMessage ?: "Failed to save"))
                }
            }
        }
    }
}
