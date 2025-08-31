package com.zamulabs.dineeasepos.table.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TableDetailsViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(TableDetailsUiState())
    val uiState: StateFlow<TableDetailsUiState> = _uiState.asStateFlow()

    fun onEvent(event: TableDetailsUiEvent){
        when(event){
            TableDetailsUiEvent.OnClickCreateOrder -> { /* navigation handled by screen */ }
            TableDetailsUiEvent.OnClickEditTable -> { /* navigation handled by screen */ }
        }
    }
}