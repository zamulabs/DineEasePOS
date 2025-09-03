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
package com.zamulabs.dineeasepos.ui.table.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamulabs.dineeasepos.data.DineEaseRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TableDetailsViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TableDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<TableDetailsUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: TableDetailsUiState.() -> TableDetailsUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: TableDetailsUiEvent) {
        when (event) {
            TableDetailsUiEvent.OnClickCreateOrder -> _uiEffect.trySend(TableDetailsUiEffect.NavigateToNewOrder)
            TableDetailsUiEvent.OnClickEditTable -> _uiEffect.trySend(TableDetailsUiEffect.NavigateToEditTable)
        }
    }

    fun loadTableDetails(tableId: String) {
        // Placeholder for future repository-backed details fetching if API exists.
        viewModelScope.launch {
            // e.g., when(repository.getTableDetails(tableId)) { ... }
        }
    }
}
