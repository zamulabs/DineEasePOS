package com.zamulabs.dineeasepos.table.addtable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddTableViewModel: ViewModel() {
    var uiState by mutableStateOf(AddTableUiState())
        private set

    fun onEvent(event: AddTableUiEvent){
        when(event){
            is AddTableUiEvent.OnTableNumberChanged -> uiState = uiState.copy(tableNumber = event.value)
            is AddTableUiEvent.OnTableNameChanged -> uiState = uiState.copy(tableName = event.value)
            is AddTableUiEvent.OnCapacityChanged -> uiState = uiState.copy(capacity = event.value.filter { it.isDigit() })
            is AddTableUiEvent.OnLocationChanged -> uiState = uiState.copy(location = event.value)
            AddTableUiEvent.OnCancel -> { /* navigation handled by screen */ }
            AddTableUiEvent.OnSave -> { /* repository call would go here */ }
        }
    }
}
