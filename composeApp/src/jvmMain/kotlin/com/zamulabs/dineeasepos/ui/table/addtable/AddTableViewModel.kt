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
package com.zamulabs.dineeasepos.ui.table.addtable

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

class AddTableViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddTableUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<AddTableUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun updateUiState(block: AddTableUiState.() -> AddTableUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: AddTableUiEvent) {
        when (event) {
            is AddTableUiEvent.OnTableNumberChanged -> updateUiState { copy(tableNumber = event.value) }
            is AddTableUiEvent.OnTableNameChanged -> updateUiState { copy(tableName = event.value) }
            is AddTableUiEvent.OnCapacityChanged -> updateUiState { copy(capacity = event.value.filter { it.isDigit() }) }
            is AddTableUiEvent.OnLocationChanged -> updateUiState { copy(location = event.value) }
            AddTableUiEvent.OnCancel -> {
                _uiEffect.trySend(AddTableUiEffect.NavigateBack)
            }
            AddTableUiEvent.OnSave -> {
                saveTable()
            }
        }
    }

    private fun saveTable() {
        val state = _uiState.value
        val number = state.tableNumber.ifBlank { state.tableName.ifBlank { "" } }
        val capacity = state.capacity.toIntOrNull() ?: 0
        if (number.isBlank() || capacity <= 0) {
            updateUiState { copy(error = "Please enter a valid table number/name and capacity") }
            _uiEffect.trySend(AddTableUiEffect.ShowSnackBar("Invalid input"))
            return
        }
        viewModelScope.launch {
            updateUiState { copy(loading = true, error = null) }
            when (val result = repository.addTable(
                number = number,
                name = state.tableName.takeIf { it.isNotBlank() },
                capacity = capacity,
                location = state.location.takeIf { it.isNotBlank() && it != state.locations.first() }
            )) {
                is NetworkResult.Error -> {
                    updateUiState { copy(loading = false, error = result.errorMessage) }
                    _uiEffect.trySend(AddTableUiEffect.ShowSnackBar(result.errorMessage ?: "Failed to add table"))
                }
                is NetworkResult.Success -> {
                    updateUiState { copy(loading = false) }
                    _uiEffect.trySend(AddTableUiEffect.ShowToast("Table added"))
                    _uiEffect.trySend(AddTableUiEffect.NavigateBack)
                }
            }
        }
    }
}
