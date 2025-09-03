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
package com.zamulabs.dineeasepos.ui.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OrderManagementViewModel : ViewModel() {
    var uiState by mutableStateOf(sampleState())
        private set

    fun onEvent(event: OrderManagementUiEvent) {
        when (event) {
            OrderManagementUiEvent.OnClickNewOrder -> {
                // Future: navigate or create new order
            }
            is OrderManagementUiEvent.OnSearch -> {
                uiState = uiState.copy(searchString = event.query)
            }
            is OrderManagementUiEvent.OnTabSelected -> {
                uiState = uiState.copy(selectedTab = event.tab)
            }
            is OrderManagementUiEvent.OnClickViewDetails -> {
                // Future: handle selection or navigation via effect/state
            }
        }
    }

    private fun sampleState(): OrderManagementUiState {
        val items =
            listOf(
                Order("#12345", "Table 5", OrderStatus.Open, "$50.00", "10:00 AM"),
                Order("#12346", "Table 2", OrderStatus.Completed, "$75.00", "11:30 AM"),
                Order("#12347", "Table 8", OrderStatus.Open, "$30.00", "12:15 PM"),
                Order("#12348", "Table 1", OrderStatus.Completed, "$100.00", "1:45 PM"),
                Order("#12349", "Table 3", OrderStatus.Open, "$45.00", "2:30 PM"),
                Order("#12350", "Table 6", OrderStatus.Completed, "$60.00", "3:00 PM"),
                Order("#12351", "Table 4", OrderStatus.Open, "$80.00", "4:15 PM"),
                Order("#12352", "Table 7", OrderStatus.Completed, "$90.00", "5:00 PM"),
                Order("#12353", "Table 9", OrderStatus.Open, "$25.00", "5:45 PM"),
                Order("#12354", "Table 10", OrderStatus.Completed, "$120.00", "6:30 PM"),
            )
        return OrderManagementUiState(orders = items)
    }
}
