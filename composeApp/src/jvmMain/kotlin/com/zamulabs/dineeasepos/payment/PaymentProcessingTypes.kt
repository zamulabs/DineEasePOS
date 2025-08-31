package com.zamulabs.dineeasepos.payment

import androidx.compose.runtime.Immutable

@Immutable
data class PaymentProcessingUiState(
    val orderId: String = "#1234",
    val subtotal: String = "$45.00",
    val tax: String = "$5.00",
    val total: String = "$50.00",
    val method: PaymentMethod = PaymentMethod.Cash,
    val amountReceived: String = "",
    val changeDue: String = "$0.00",
    val onlineGateway: String = "",
    val transactionStatus: String = "Pending",
)

enum class PaymentMethod { Cash, Online }

sealed interface PaymentProcessingUiEvent {
    data class OnAmountChanged(val value: String): PaymentProcessingUiEvent
    data class OnMethodSelected(val method: PaymentMethod): PaymentProcessingUiEvent
    data class OnOnlineGatewaySelected(val gateway: String): PaymentProcessingUiEvent
    data object OnProcessPayment: PaymentProcessingUiEvent
    data object OnBack: PaymentProcessingUiEvent
}
