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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamulabs.dineeasepos.data.DineEaseRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuDetailsViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<MenuDetailsUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: MenuDetailsUiState.() -> MenuDetailsUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: MenuDetailsUiEvent) {
        when (event) {
            MenuDetailsUiEvent.Edit -> _uiEffect.trySend(MenuDetailsUiEffect.NavigateToEdit)
            MenuDetailsUiEvent.ToggleActive -> updateUiState { copy(active = !active) }
            MenuDetailsUiEvent.Delete -> {
                // Placeholder delete behavior
                viewModelScope.launch {
                    _uiEffect.trySend(MenuDetailsUiEffect.ShowSnackBar("Delete not implemented"))
                }
            }
        }
    }

    fun loadMenuDetails(itemId: String? = null) {
        viewModelScope.launch {
            // A simple sample: load first item if available
            val items = repository.getMenu().data.orEmpty()
            val item = if (itemId != null) items.find { it.name == itemId } else items.firstOrNull()
            item?.let { mi ->
                updateUiState {
                    copy(
                        id = "#${mi.name.hashCode()}",
                        name = mi.name,
                        category = mi.category,
                        price = mi.price,
                        active = mi.active,
                        description = ""
                    )
                }
            }
        }
    }
}
