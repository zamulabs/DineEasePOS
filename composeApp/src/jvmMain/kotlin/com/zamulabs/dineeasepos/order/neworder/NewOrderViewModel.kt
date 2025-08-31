package com.zamulabs.dineeasepos.order.neworder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NewOrderViewModel: ViewModel() {
    var uiState by mutableStateOf(NewOrderUiState(tables = listOf("Select Table","Table 1","Table 2","Table 3")))
        private set

    fun onEvent(event: NewOrderUiEvent){
        when(event){
            is NewOrderUiEvent.OnSearch -> uiState = uiState.copy(searchString = event.query)
            is NewOrderUiEvent.OnTableSelected -> uiState = uiState.copy(selectedTable = event.table)
            is NewOrderUiEvent.OnCategorySelected -> uiState = uiState.copy(selectedCategoryIndex = event.index)
            is NewOrderUiEvent.OnNotesChanged -> uiState = uiState.copy(notes = event.notes)
        }
    }
}
