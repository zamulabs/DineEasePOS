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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportsViewModel(
    private val repository: com.zamulabs.dineeasepos.data.DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ReportsUiState(
            rows = emptyList(),
        )
    )
    val uiState: StateFlow<ReportsUiState> = _uiState

    private val _uiEffect = Channel<ReportsUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun updateUiState(block: ReportsUiState.() -> ReportsUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: ReportsUiEvent) {
        when (event) {
            is ReportsUiEvent.OnTabSelected -> {
                updateUiState { copy(selectedTab = event.tab) }
                if (event.tab == ReportsTab.ItemPerformance) {
                    getCombinationsReport()
                }
            }
            is ReportsUiEvent.OnPeriodChanged -> {
                updateUiState { copy(period = event.period) }
                getSalesReports()
            }
            is ReportsUiEvent.OnCombinationsRangeChanged -> {
                updateUiState { copy(fromIso = event.fromIso, toIso = event.toIso, limit = event.limit) }
                getCombinationsReport()
            }
            ReportsUiEvent.OnExport -> {
                exportCurrentReport()
            }
            ReportsUiEvent.OnPrint -> {
                viewModelScope.launch { _uiEffect.send(ReportsUiEffect.ShowSnackBar("Sent to printer")) }
            }
        }
    }

    fun getSalesReports() {
        viewModelScope.launch {
            val period = _uiState.value.period
            fun parseCurrencyToDouble(s: String): Double? = try { s.replace("KES", "").replace("$", "").replace(",", "").trim().toDouble() } catch (_: Throwable) { null }
            fun formatKes(amount: Double): String = "KES " + String.format("%.2f", amount)
            when (val result = repository.getSalesReports(period)) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    updateUiState { /* keep previous rows */ this }
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    val rows = result.data.orEmpty()
                    val totalNet = rows.mapNotNull { r -> parseCurrencyToDouble(r.net) }.sum()
                    val totalStr = if (rows.isNotEmpty()) formatKes(totalNet) else "KES 0.00"
                    updateUiState { copy(rows = rows, totalSales = totalStr) }
                }
            }
        }
    }

    private fun exportCurrentReport() {
        // Minimal CSV export to clipboard-like feedback: compose CSV string; in real app we would write to file
        val state = _uiState.value
        val csv = buildString {
            appendLine("Report: ${state.selectedTab}")
            if (state.selectedTab == ReportsTab.Sales) {
                appendLine("Date,Orders,Gross,Discounts,Net")
                state.rows.forEach { r ->
                    appendLine("${r.date},${r.orders},${r.gross},${r.discounts},${r.net}")
                }
            } else if (state.selectedTab == ReportsTab.ItemPerformance) {
                appendLine("Item A,Item B,Count")
                state.combinations.forEach { c ->
                    appendLine("${c.itemAName},${c.itemBName},${c.count}")
                }
            }
        }
        viewModelScope.launch {
            _uiEffect.send(ReportsUiEffect.ShowSnackBar("Exported report (${csv.lines().size - 1} rows)"))
        }
    }

    fun getCombinationsReport() {
        viewModelScope.launch {
            val from = _uiState.value.fromIso.ifBlank { "2025-01-01" }
            val to = _uiState.value.toIso.ifBlank { "2025-12-31" }
            val lim = _uiState.value.limit
            when (val result = repository.generateCombinationsReport(from, to, lim)) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    // keep previous combinations
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    updateUiState { copy(combinations = result.data.orEmpty()) }
                }
            }
        }
    }
}
