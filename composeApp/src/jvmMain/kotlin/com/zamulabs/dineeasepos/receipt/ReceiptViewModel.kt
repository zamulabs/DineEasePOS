package com.zamulabs.dineeasepos.receipt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ReceiptViewModel: ViewModel() {
    var uiState by mutableStateOf(ReceiptUiState())
        private set

    fun onEvent(event: ReceiptUiEvent){
        when(event){
            ReceiptUiEvent.OnEmail -> { /* TODO: implement */ }
            ReceiptUiEvent.OnPrint -> { /* TODO: implement */ }
            ReceiptUiEvent.OnSavePdf -> { /* TODO: implement */ }
        }
    }
}
