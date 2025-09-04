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
package com.zamulabs.dineeasepos.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamulabs.dineeasepos.data.DineEaseRepository
import com.zamulabs.dineeasepos.utils.NetworkResult
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuManagementViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuManagementUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<MenuManagementUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: MenuManagementUiState.() -> MenuManagementUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: MenuManagementUiEvent) {
        when (event) {
            MenuManagementUiEvent.OnClickAddItem -> {
                _uiEffect.trySend(MenuManagementUiEffect.ShowToast("Hello"))
            }

            is MenuManagementUiEvent.OnSearch -> {
                updateUiState { copy(searchString = event.query) }
            }
            is MenuManagementUiEvent.OnTabSelected -> {
                updateUiState {
                    copy(
                        selectedTab = event.tab
                    )
                }
            }

            is MenuManagementUiEvent.OnToggleActive -> {
                // Call update to toggle active on backend
                val current = uiState.value.items.find { it.name == event.itemName }
                if (current != null) {
                    viewModelScope.launch {
                        val price = current.price.filter { it.isDigit() || it == '.' }.toDoubleOrNull() ?: 0.0
                        when (val res = repository.updateMenuItem(
                            name = current.name,
                            description = null,
                            price = price,
                            category = current.category,
                            active = !current.active,
                            prepTimeMinutes = null,
                            ingredients = null,
                        )) {
                            is NetworkResult.Success -> {
                                val updated = uiState.value.items.map { item ->
                                    if (item.name == event.itemName) item.copy(active = !item.active) else item
                                }
                                updateUiState { copy(items = updated, selectedItem = updated.find { it.name == selectedItem?.name }) }
                            }
                            is NetworkResult.Error -> {
                                _uiEffect.trySend(MenuManagementUiEffect.ShowSnackBar(res.errorMessage ?: "Failed to update status"))
                            }
                        }
                    }
                }
            }

            is MenuManagementUiEvent.OnEdit -> {
                // Select item so side pane can prefill via AddMenuItemViewModel.setEditing
                val sel = uiState.value.items.find { it.name == event.itemName }
                updateUiState { copy(selectedItem = sel, showAddMenu = true) }
            }

            is MenuManagementUiEvent.OnDelete -> {
                val name = event.itemName
                viewModelScope.launch {
                    when (val res = repository.deleteMenuItem(name)) {
                        is NetworkResult.Success -> {
                            getMenuItems()
                            _uiEffect.trySend(MenuManagementUiEffect.ShowSnackBar("Item deleted"))
                        }
                        is NetworkResult.Error -> _uiEffect.trySend(MenuManagementUiEffect.ShowSnackBar(res.errorMessage ?: "Delete failed"))
                    }
                }
            }

            is MenuManagementUiEvent.OnClickViewDetails -> {
                val sel = uiState.value.items.find { it.name == event.itemName }
                updateUiState { copy(selectedItem = sel) }
            }
        }
    }

    fun getMenuItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMenuItems = true) }

            when (val result = repository.getMenu()) {
                is NetworkResult.Error -> {
                    Napier.e("Error fetching menu")
                    _uiState.update {
                        it.copy(
                            isLoadingMenuItems = false,
                            errorLoadingMenuItems = result.errorMessage,
                        )
                    }
                }

                is NetworkResult.Success -> {
                    Napier.e("success fetching menu")
                    _uiState.update {
                        it.copy(
                            isLoadingMenuItems = false,
                            items = result.data.orEmpty(),
                        )
                    }
                }
            }
        }
    }

}
