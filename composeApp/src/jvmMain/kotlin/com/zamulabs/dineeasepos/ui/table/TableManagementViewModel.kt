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
package com.zamulabs.dineeasepos.ui.table

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

class TableManagementViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TableManagementUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<TableManagementUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: TableManagementUiState.() -> TableManagementUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: TableManagementUiEvent) {
        when (event) {
            is TableManagementUiEvent.OnClickAddTable -> {
                // Open the side pane to show Add Table form
                updateUiState { copy(showAddTable = true) }
            }

            is TableManagementUiEvent.OnSearch -> {
                updateUiState { copy(searchString = event.query) }
            }

            is TableManagementUiEvent.OnClickViewDetails -> {
                val selected = uiState.value.tables.find { it.number == event.tableNumber }
                updateUiState { copy(selectedTable = selected, showAddTable = false) }
            }
        }
    }

    fun loadTables() {
        viewModelScope.launch {
            updateUiState { copy(isLoadingTables = true) }
            when (val result = repository.getTables()) {
                is NetworkResult.Error -> {
                    updateUiState {
                        copy(
                            isLoadingTables = false,
                            errorLoadingTables = result.errorMessage,
                            tables = sampleTables(),
                        )
                    }
                }

                is NetworkResult.Success -> {
                    updateUiState {
                        copy(
                            isLoadingTables = false,
                            tables = result.data ?: sampleTables(),
                        )
                    }
                }
            }
        }
    }
}
