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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PaymentProcessingViewModel : ViewModel() {
    var uiState by mutableStateOf(PaymentProcessingUiState())
        private set

    fun onEvent(event: PaymentProcessingUiEvent) {
        when (event) {
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
