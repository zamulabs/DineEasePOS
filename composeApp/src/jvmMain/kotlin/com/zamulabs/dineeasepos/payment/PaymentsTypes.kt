package com.zamulabs.dineeasepos.payment

import androidx.compose.runtime.Immutable

@Immutable
data class PaymentItem(
    val date: String,
    val orderId: String,
    val method: String,
    val amount: String,
    val status: String = "Completed",
)

@Immutable
data class PaymentsUiState(
    val items: List<PaymentItem> = emptyList(),
    val filter: String = "",
)

sealed interface PaymentsUiEvent {
    data class OnFilterChanged(val value: String): PaymentsUiEvent
    data object OnExport: PaymentsUiEvent
}
