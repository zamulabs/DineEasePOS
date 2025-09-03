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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel(
    private val repository: com.zamulabs.dineeasepos.data.DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<DashboardUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: DashboardUiState.() -> DashboardUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: DashboardUiEvent) {
        when (event) {
            DashboardUiEvent.Refresh -> refresh()
        }
    }

    fun refresh() {
        // Align with Menu feature: use repository + loading/error pattern
        _uiState.update { it.copy(isLoadingDashboard = true, errorLoadingDashboard = null) }
        viewModelScope.launch {
            // Fetch orders and payments; compute metrics
            val ordersResult = repository.getOrders()
            val paymentsResult = repository.getPayments()

            var totalSalesToday = "$0.00"
            var ordersProcessed = "0"
            var pendingOrders = "0"
            var cashVsOnline = "0% / 0%"
            var recentOrders: List<RecentOrder> = emptyList()
            var error: String? = null

            when (ordersResult) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    val orders = ordersResult.data.orEmpty()
                    ordersProcessed = orders.size.toString()
                    pendingOrders = orders.count { it.status.name.equals("Open", ignoreCase = true) }.toString()
                    recentOrders = orders.take(20).map {
                        RecentOrder(
                            id = it.id,
                            status = it.status.readable(),
                            customer = it.table, // Using table as a surrogate for customer for now
                            time = it.time,
                            total = it.total,
                        )
                    }
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    error = (error?.let { it + "\n" }.orEmpty()) + (ordersResult.errorMessage ?: "Failed to load orders")
                }
            }

            when (paymentsResult) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    val payments = paymentsResult.data.orEmpty()
                    // Sum amounts
                    val amounts = payments.mapNotNull { it.amount.toCurrencyValueOrNull() }
                    val total = amounts.sum()
                    totalSalesToday = "$" + String.format("%.2f", total)

                    // Compute cash vs online split
                    val cashTotal = payments.filter { it.method.contains("cash", ignoreCase = true) }
                        .mapNotNull { it.amount.toCurrencyValueOrNull() }.sum()
                    val nonCashTotal = total - cashTotal
                    val cashPct = if (total > 0.0) (cashTotal / total) * 100.0 else 0.0
                    val nonCashPct = 100.0 - cashPct
                    cashVsOnline = String.format("%.0f%% / %.0f%%", cashPct, nonCashPct)
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    error = (error?.let { it + "\n" }.orEmpty()) + (paymentsResult.errorMessage ?: "Failed to load payments")
                }
            }

            _uiState.update {
                it.copy(
                    isLoadingDashboard = false,
                    errorLoadingDashboard = error,
                    totalSalesToday = totalSalesToday,
                    ordersProcessed = ordersProcessed,
                    pendingOrders = pendingOrders,
                    cashVsOnline = cashVsOnline,
                    recentOrders = recentOrders.ifEmpty { it.recentOrders },
                )
            }

            if (!error.isNullOrBlank()) {
                _uiEffect.trySend(DashboardUiEffect.ShowSnackBar(error!!))
            }
        }
    }
}

private fun com.zamulabs.dineeasepos.ui.order.OrderStatus.readable(): String = when (this) {
    com.zamulabs.dineeasepos.ui.order.OrderStatus.Open -> "Open"
    com.zamulabs.dineeasepos.ui.order.OrderStatus.Completed -> "Completed"
}

private fun String.toCurrencyValueOrNull(): Double? {
    return try {
        this.replace("$", "").replace(",", "").trim().toDouble()
    } catch (_: Throwable) { null }
}
