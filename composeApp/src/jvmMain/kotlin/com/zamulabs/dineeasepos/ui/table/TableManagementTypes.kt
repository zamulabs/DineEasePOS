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

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable

@Immutable
data class DiningTable(
    val number: String,
    val capacity: Int,
    val status: TableStatus,
)

enum class TableStatus { Available, Occupied }

@Immutable
data class TableManagementUiState(
    val searchString: String = "",

    val isLoadingTables: Boolean = false,
    val errorLoadingTables: String? = null,
    val tables: List<DiningTable> = emptyList(),
    val selectedTable: DiningTable? = null,
    val showAddTable: Boolean = false,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)

sealed interface TableManagementUiEvent {
    data object OnClickAddTable : TableManagementUiEvent

    data class OnSearch(
        val query: String,
    ) : TableManagementUiEvent

    data class OnClickViewDetails(
        val tableNumber: String,
    ) : TableManagementUiEvent
}

sealed class TableManagementUiEffect {
    data class ShowToast(val message: String) : TableManagementUiEffect()
    data class ShowSnackBar(val message: String) : TableManagementUiEffect()
    data object NavigateBack : TableManagementUiEffect()
}

fun sampleTables(): List<DiningTable> =
    listOf(
        DiningTable("Table 1", 4, TableStatus.Available),
        DiningTable("Table 2", 2, TableStatus.Occupied),
        DiningTable("Table 3", 6, TableStatus.Available),
        DiningTable("Table 4", 4, TableStatus.Occupied),
        DiningTable("Table 5", 2, TableStatus.Available),
    )
