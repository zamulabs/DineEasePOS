package com.zamulabs.dineeasepos.receipt

import androidx.compose.runtime.Immutable

@Immutable
data class ReceiptItem(
    val item: String,
    val quantity: Int,
    val price: String,
    val total: String,
)

@Immutable
data class ReceiptUiState(
    val orderId: String = "#1234567890",
    val restaurantName: String = "The Golden Spoon",
    val address: String = "123 Main Street, Anytown, USA",
    val phone: String = "(555) 123-4567",
    val orderDate: String = "July 26, 2024",
    val orderTime: String = "7:30 PM",
    val orderType: String = "Dine-in",
    val items: List<ReceiptItem> = listOf(
        ReceiptItem("Classic Burger",1,"$12.99","$12.99"),
        ReceiptItem("Fries",1,"$4.99","$4.99"),
        ReceiptItem("Soda",1,"$2.50","$2.50"),
    ),
    val subtotal: String = "$20.48",
    val tax: String = "$1.50",
    val total: String = "$21.98",
    val paymentMethod: String = "Credit Card",
)

sealed interface ReceiptUiEvent {
    data object OnPrint : ReceiptUiEvent
    data object OnSavePdf : ReceiptUiEvent
    data object OnEmail : ReceiptUiEvent
}
