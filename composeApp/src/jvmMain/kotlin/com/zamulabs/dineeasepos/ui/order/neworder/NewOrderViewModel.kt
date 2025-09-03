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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NewOrderViewModel : ViewModel() {
    var uiState by mutableStateOf(NewOrderUiState(tables = listOf("Select Table", "Table 1", "Table 2", "Table 3")))
        private set

    fun onEvent(event: NewOrderUiEvent) {
        when (event) {
            is NewOrderUiEvent.OnSearch -> uiState = uiState.copy(searchString = event.query)
            is NewOrderUiEvent.OnTableSelected -> uiState = uiState.copy(selectedTable = event.table)
            is NewOrderUiEvent.OnCategorySelected -> uiState = uiState.copy(selectedCategoryIndex = event.index)
            is NewOrderUiEvent.OnNotesChanged -> uiState = uiState.copy(notes = event.notes)
        }
    }
}
