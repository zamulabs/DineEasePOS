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
package com.zamulabs.dineeasepos.ui.dashboard

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable

// UI state for the Dashboard
@Immutable
data class DashboardUiState(
    val totalSalesToday: String = "KES 0.00",
    val ordersProcessed: String = "0",
    val pendingOrders: String = "0",
    val cashVsOnline: String = "0% / 100%",
    val topSelling: List<TopSellingItem> = emptyList(),
    val recentOrders: List<RecentOrder> = emptyList(),
    val stockSummary: List<StockSummaryRow> = emptyList(),
    val isLoadingDashboard: Boolean = false,
    val errorLoadingDashboard: String? = null,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)

sealed interface DashboardUiEvent {
    data object Refresh : DashboardUiEvent
}

sealed class DashboardUiEffect {
    data class ShowToast(val message: String) : DashboardUiEffect()
    data class ShowSnackBar(val message: String) : DashboardUiEffect()
    data object NavigateBack : DashboardUiEffect()
}

data class TopSellingItem(
    val item: String,
    val qty: String,
    val revenue: String,
)

data class RecentOrder(
    val id: String,
    val status: String,
    val customer: String,
    val time: String,
    val total: String,
)

data class StockSummaryRow(
    val item: String,
    val prepared: String,
    val sold: String,
    val remaining: String,
)
