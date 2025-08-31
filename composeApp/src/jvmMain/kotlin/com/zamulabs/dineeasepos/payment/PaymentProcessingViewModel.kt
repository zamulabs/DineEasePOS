package com.zamulabs.dineeasepos.payment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PaymentProcessingViewModel: ViewModel() {
    var uiState by mutableStateOf(PaymentProcessingUiState())
        private set

    fun onEvent(event: PaymentProcessingUiEvent){
        when(event){
            is PaymentProcessingUiEvent.OnAmountChanged -> {
                uiState = uiState.copy(amountReceived = event.value, changeDue = calculateChange(event.value))
            }
            is PaymentProcessingUiEvent.OnMethodSelected -> uiState = uiState.copy(method = event.method)
            is PaymentProcessingUiEvent.OnOnlineGatewaySelected -> uiState = uiState.copy(onlineGateway = event.gateway)
            PaymentProcessingUiEvent.OnProcessPayment -> {
                uiState = uiState.copy(transactionStatus = if (uiState.method == PaymentMethod.Online) "Processing" else "Completed")
            }
            PaymentProcessingUiEvent.OnBack -> {}
        }
    }

    private fun calculateChange(input: String): String {
        val received = input.toDoubleOrNull() ?: 0.0
        val total = uiState.total.replace("$", "").toDoubleOrNull() ?: 0.0
        val change = (received - total).coerceAtLeast(0.0)
        return "$" + String.format("%.2f", change)
        }
}
