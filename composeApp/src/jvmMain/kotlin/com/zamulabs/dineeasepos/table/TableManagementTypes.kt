package com.zamulabs.dineeasepos.table

import androidx.compose.runtime.Immutable

@Immutable
data class DiningTable(
    val number: String,
    val capacity: Int,
    val status: TableStatus,
)

enum class TableStatus { Available, Occupied }

@Immutable
data class TableManagementUiState(
    val tables: List<DiningTable> = sampleTables(),
    val searchString: String = "",
)

sealed interface TableManagementUiEvent {
    data object OnClickAddTable : TableManagementUiEvent
    data class OnSearch(val query: String) : TableManagementUiEvent
    data class OnClickViewDetails(val tableNumber: String) : TableManagementUiEvent
}

fun sampleTables(): List<DiningTable> = listOf(
    DiningTable("Table 1", 4, TableStatus.Available),
    DiningTable("Table 2", 2, TableStatus.Occupied),
    DiningTable("Table 3", 6, TableStatus.Available),
    DiningTable("Table 4", 4, TableStatus.Occupied),
    DiningTable("Table 5", 2, TableStatus.Available),
)
