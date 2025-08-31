package com.zamulabs.dineeasepos.table.details

import androidx.compose.runtime.Immutable

@Immutable
data class TableOrderItem(
    val name: String,
    val quantity: Int,
    val price: String,
    val total: String,
)

@Immutable
data class TableDetailsUiState(
    val tableNumber: String = "Table 1",
    val capacity: Int = 4,
    val items: List<TableOrderItem> = listOf(
        TableOrderItem("Burger",1,"$12.00","$12.00"),
        TableOrderItem("Fries",1,"$4.00","$4.00"),
        TableOrderItem("Soda",1,"$2.00","$2.00"),
    ),
    val subtotal: String = "$18.00",
    val tax: String = "$1.44",
    val total: String = "$19.44",
)

sealed interface TableDetailsUiEvent{
    data object OnClickEditTable: TableDetailsUiEvent
    data object OnClickCreateOrder: TableDetailsUiEvent
}
