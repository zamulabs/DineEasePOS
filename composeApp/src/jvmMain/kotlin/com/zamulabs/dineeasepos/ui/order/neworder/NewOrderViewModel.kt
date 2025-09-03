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
package com.zamulabs.dineeasepos.ui.order.neworder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamulabs.dineeasepos.data.DineEaseRepository
import com.zamulabs.dineeasepos.utils.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewOrderViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewOrderUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<NewOrderUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: NewOrderUiState.() -> NewOrderUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: NewOrderUiEvent) {
        when (event) {
            is NewOrderUiEvent.OnSearch -> updateUiState { copy(searchString = event.query) }
            is NewOrderUiEvent.OnTableSelected -> updateUiState { copy(selectedTable = event.table) }
            is NewOrderUiEvent.OnCategorySelected -> updateUiState { copy(selectedCategoryIndex = event.index) }
            is NewOrderUiEvent.OnNotesChanged -> updateUiState { copy(notes = event.notes) }
        }
    }

    fun loadData() {
        viewModelScope.launch {
            // Load tables
            updateUiState { copy(isLoadingTables = true) }
            when (val tablesRes = repository.getTables()) {
                is NetworkResult.Error -> {
                    updateUiState {
                        copy(
                            isLoadingTables = false,
                            errorLoadingTables = tablesRes.errorMessage,
                            tables = listOf("Select Table")
                        )
                    }
                }
                is NetworkResult.Success -> {
                    val tableLabels = listOf("Select Table") + (tablesRes.data?.map { it.number } ?: emptyList())
                    updateUiState { copy(isLoadingTables = false, tables = tableLabels) }
                }
            }

            // Load menu items to display
            updateUiState { copy(isLoadingMenuItems = true) }
            when (val menuRes = repository.getMenu()) {
                is NetworkResult.Error -> {
                    updateUiState {
                        copy(
                            isLoadingMenuItems = false,
                            errorLoadingMenuItems = menuRes.errorMessage,
                            items = emptyList()
                        )
                    }
                }
                is NetworkResult.Success -> {
                    val items = menuRes.data?.map {
                        MenuItem(
                            title = it.name,
                            description = it.category,
                            imageUrl = "" // no image from API
                        )
                    } ?: emptyList()
                    updateUiState { copy(isLoadingMenuItems = false, items = items) }
                }
            }
        }
    }
}
