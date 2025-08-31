package com.zamulabs.dineeasepos.order.details

import androidx.compose.runtime.Immutable

@Immutable
data class OrderItem(
    val name: String,
    val quantity: Int,
    val price: String,
)

@Immutable
data class OrderDetailsUiState(
    val orderId: String = "#1234",
    val placedOn: String = "July 15, 2024 at 6:30 PM",
    val customer: String = "Sarah Chen",
    val table: String = "Table 5",
    val server: String = "Mark Davis",
    val items: List<OrderItem> = listOf(
        OrderItem("Spicy Tuna Roll", 2, "$16.00"),
        OrderItem("Miso Soup", 1, "$5.00"),
        OrderItem("Green Tea", 2, "$4.00"),
    ),
    val subtotal: String = "$25.00",
    val tax: String = "$2.50",
    val total: String = "$27.50",
    val orderStatus: String = "Placed",
    val paymentStatus: String = "Paid",
    val paymentMethod: String = "Cash",
)

sealed interface OrderDetailsUiEvent {
    data object MarkPreparing : OrderDetailsUiEvent
    data object MarkReady : OrderDetailsUiEvent
    data object Complete : OrderDetailsUiEvent
    data object Cancel : OrderDetailsUiEvent
}
