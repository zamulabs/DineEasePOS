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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ReportsViewModel : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            ReportsUiState(
                rows = demoRows(),
            ),
        )
    val uiState: StateFlow<ReportsUiState> = _uiState

    fun onEvent(event: ReportsUiEvent) {
        when (event) {
            is ReportsUiEvent.OnTabSelected -> _uiState.update { it.copy(selectedTab = event.tab) }
            is ReportsUiEvent.OnPeriodChanged -> _uiState.update { it.copy(period = event.period) }
            ReportsUiEvent.OnExport -> { /* no-op for now */ }
        }
    }
}

private fun demoRows() =
    listOf(
        SalesRow("June 1, 2024", 120, "$3,500", "$200", "$3,300"),
        SalesRow("June 2, 2024", 110, "$3,200", "$150", "$3,050"),
        SalesRow("June 3, 2024", 130, "$3,800", "$250", "$3,550"),
        SalesRow("June 4, 2024", 105, "$3,000", "$100", "$2,900"),
        SalesRow("June 5, 2024", 125, "$3,600", "$220", "$3,380"),
    )
