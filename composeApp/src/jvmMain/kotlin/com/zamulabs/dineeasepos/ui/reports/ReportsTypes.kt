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
package com.zamulabs.dineeasepos.ui.reports

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
    data class OnTabSelected(
        val tab: ReportsTab,
    ) : ReportsUiEvent

    data class OnPeriodChanged(
        val period: String,
    ) : ReportsUiEvent

    data object OnExport : ReportsUiEvent
}
