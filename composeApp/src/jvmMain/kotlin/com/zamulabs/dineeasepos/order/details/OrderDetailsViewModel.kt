package com.zamulabs.dineeasepos.order.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderDetailsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(OrderDetailsUiState())
    val uiState: StateFlow<OrderDetailsUiState> = _uiState.asStateFlow()

    fun onEvent(event: OrderDetailsUiEvent){
        when(event){
            OrderDetailsUiEvent.Cancel -> { /* update status if needed */ }
            OrderDetailsUiEvent.Complete -> { /* update status if needed */ }
            OrderDetailsUiEvent.MarkPreparing -> { /* update status if needed */ }
            OrderDetailsUiEvent.MarkReady -> { /* update status if needed */ }
        }
    }
}
