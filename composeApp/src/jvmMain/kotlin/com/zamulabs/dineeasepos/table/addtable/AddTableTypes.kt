package com.zamulabs.dineeasepos.table.addtable

import androidx.compose.runtime.Immutable

@Immutable
data class AddTableUiState(
    val tableNumber: String = "",
    val tableName: String = "",
    val capacity: String = "",
    val location: String = "",
    val locations: List<String> = listOf("Select location","Main Hall","Patio","VIP"),
    val loading: Boolean = false,
    val error: String? = null,
)

sealed interface AddTableUiEvent {
    data class OnTableNumberChanged(val value: String): AddTableUiEvent
    data class OnTableNameChanged(val value: String): AddTableUiEvent
    data class OnCapacityChanged(val value: String): AddTableUiEvent
    data class OnLocationChanged(val value: String): AddTableUiEvent
    data object OnCancel: AddTableUiEvent
    data object OnSave: AddTableUiEvent
}
