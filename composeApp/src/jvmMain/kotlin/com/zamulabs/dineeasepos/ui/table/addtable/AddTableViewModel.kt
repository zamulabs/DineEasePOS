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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddTableViewModel : ViewModel() {
    var uiState by mutableStateOf(AddTableUiState())
        private set

    fun onEvent(event: AddTableUiEvent) {
        when (event) {
            is AddTableUiEvent.OnTableNumberChanged -> uiState = uiState.copy(tableNumber = event.value)
            is AddTableUiEvent.OnTableNameChanged -> uiState = uiState.copy(tableName = event.value)
            is AddTableUiEvent.OnCapacityChanged -> uiState = uiState.copy(capacity = event.value.filter { it.isDigit() })
            is AddTableUiEvent.OnLocationChanged -> uiState = uiState.copy(location = event.value)
            AddTableUiEvent.OnCancel -> { /* navigation handled by screen */ }
            AddTableUiEvent.OnSave -> { /* repository call would go here */ }
        }
    }
}
