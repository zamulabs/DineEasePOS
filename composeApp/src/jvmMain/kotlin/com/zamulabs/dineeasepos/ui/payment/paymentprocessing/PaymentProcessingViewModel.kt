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
package com.zamulabs.dineeasepos.ui.payment.paymentprocessing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentProcessingViewModel(
    private val repository: com.zamulabs.dineeasepos.data.DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PaymentProcessingUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<PaymentProcessingUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun updateUiState(block: PaymentProcessingUiState.() -> PaymentProcessingUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: PaymentProcessingUiEvent) {
        when (event) {
            is PaymentProcessingUiEvent.OnAmountChanged -> {
                updateUiState { copy(amountReceived = event.value, changeDue = calculateChange(event.value)) }
            }
            is PaymentProcessingUiEvent.OnMethodSelected -> updateUiState { copy(method = event.method) }
            is PaymentProcessingUiEvent.OnOnlineGatewaySelected -> updateUiState { copy(onlineGateway = event.gateway) }
            PaymentProcessingUiEvent.OnProcessPayment -> processPayment()
            PaymentProcessingUiEvent.OnBack -> { /* handled by Screen */ }
        }
    }

    private fun processPayment() {
        viewModelScope.launch {
            val current = _uiState.value
            updateUiState { copy(transactionStatus = if (current.method == PaymentMethod.Online) "Processing" else "Processing") }
            val amount = current.amountReceived.toDoubleOrNull()
            val result = repository.processPayment(
                orderId = current.orderId,
                method = current.method,
                amountReceived = amount,
                gateway = current.onlineGateway.ifBlank { null },
            )
            when (result) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    updateUiState { copy(transactionStatus = "Failed") }
                    _uiEffect.trySend(PaymentProcessingUiEffect.ShowSnackBar(result.errorMessage ?: "Payment failed"))
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    val status = result.data ?: "Completed"
                    updateUiState { copy(transactionStatus = status) }
                    _uiEffect.trySend(PaymentProcessingUiEffect.NavigateToReceipt(orderId = current.orderId))
                }
            }
        }
    }

    private fun calculateChange(input: String): String {
        val received = input.toDoubleOrNull() ?: 0.0
        val total = _uiState.value.total.replace("$", "").toDoubleOrNull() ?: 0.0
        val change = (received - total).coerceAtLeast(0.0)
        return "$" + String.format("%.2f", change)
    }
}
