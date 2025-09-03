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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TableManagementViewModel : ViewModel() {
    var uiState by mutableStateOf(TableManagementUiState())
        private set

    fun onEvent(event: TableManagementUiEvent) {
        when (event) {
            is TableManagementUiEvent.OnClickAddTable -> {
                // In a real app, navigate/open dialog. For now, append a sample new table.
                val nextNumber = (uiState.tables.size + 1)
                uiState =
                    uiState.copy(
                        tables = uiState.tables + DiningTable("Table $nextNumber", 4, TableStatus.Available),
                    )
            }
            is TableManagementUiEvent.OnSearch -> {
                uiState = uiState.copy(searchString = event.query)
            }

            is TableManagementUiEvent.OnClickViewDetails -> {
            }
        }
    }
}
