package com.zamulabs.dineeasepos.reports

import androidx.compose.runtime.Immutable

@Immutable
data class SalesRow(
    val date: String,
    val orders: Int,
    val gross: String,
    val discounts: String,
    val net: String,
)

enum class ReportsTab { Sales, ItemPerformance, PaymentSummary }

@Immutable
data class ReportsUiState(
    val selectedTab: ReportsTab = ReportsTab.Sales,
    val period: String = "Last 30 Days",
    val totalSales: String = "$25,450",
    val delta: String = "+12%",
    val rows: List<SalesRow> = emptyList(),
)

sealed interface ReportsUiEvent {
    data class OnTabSelected(val tab: ReportsTab) : ReportsUiEvent
    data class OnPeriodChanged(val period: String) : ReportsUiEvent
    data object OnExport : ReportsUiEvent
}
