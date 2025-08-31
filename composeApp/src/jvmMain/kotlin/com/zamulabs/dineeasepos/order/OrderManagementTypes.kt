package com.zamulabs.dineeasepos.order

import androidx.compose.runtime.Immutable

@Immutable
data class Order(
    val id: String,
    val table: String,
    val status: OrderStatus,
    val total: String,
    val time: String,
)

enum class OrderStatus { Open, Completed }

@Immutable
data class OrderManagementUiState(
    val orders: List<Order> = emptyList(),
    val searchString: String = "",
    val selectedTab: OrderTab = OrderTab.All,
)

enum class OrderTab { All, Open, Completed }

sealed interface OrderManagementUiEvent {
    data object OnClickNewOrder : OrderManagementUiEvent
    data class OnSearch(val query: String) : OrderManagementUiEvent
    data class OnTabSelected(val tab: OrderTab) : OrderManagementUiEvent
    data class OnClickViewDetails(val orderId: String) : OrderManagementUiEvent
}

sealed interface OrderManagementUiEffect {
    data class ShowToast(val message: String) : OrderManagementUiEffect
}
