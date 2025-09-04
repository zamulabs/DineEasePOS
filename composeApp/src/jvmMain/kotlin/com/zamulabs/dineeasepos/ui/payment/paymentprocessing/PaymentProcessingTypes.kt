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

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable

@Immutable
data class PaymentProcessingUiState(
    val orderId: String = "#1234",
    val subtotal: String = "$45.00",
    val tax: String = "$3.60",
    val total: String = "$48.60",
    val method: PaymentMethod = PaymentMethod.Cash,
    val amountReceived: String = "",
    val changeDue: String = "$0.00",
    val onlineGateway: String = "",
    val transactionStatus: String = "Pending",
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)

enum class PaymentMethod { Cash, Online }

sealed interface PaymentProcessingUiEvent {
    data class OnAmountChanged(
        val value: String,
    ) : PaymentProcessingUiEvent

    data class OnMethodSelected(
        val method: PaymentMethod,
    ) : PaymentProcessingUiEvent

    data class OnOnlineGatewaySelected(
        val gateway: String,
    ) : PaymentProcessingUiEvent

    data object OnProcessPayment : PaymentProcessingUiEvent

    data object OnBack : PaymentProcessingUiEvent
}

sealed class PaymentProcessingUiEffect {
    data class ShowSnackBar(val message: String) : PaymentProcessingUiEffect()
    data class NavigateToReceipt(val orderId: String) : PaymentProcessingUiEffect()
    data object NavigateBack : PaymentProcessingUiEffect()
}
