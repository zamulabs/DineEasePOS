package com.zamulabs.dineeasepos.table

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TableManagementViewModel: ViewModel() {
    var uiState by mutableStateOf(TableManagementUiState())
        private set

    fun onEvent(event: TableManagementUiEvent){
        when(event){
            is TableManagementUiEvent.OnClickAddTable -> {
                // In a real app, navigate/open dialog. For now, append a sample new table.
                val nextNumber = (uiState.tables.size + 1)
                uiState = uiState.copy(
                    tables = uiState.tables + DiningTable("Table $nextNumber", 4, TableStatus.Available)
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
