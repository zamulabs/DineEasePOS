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
package com.zamulabs.dineeasepos.ui.payment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PaymentsViewModel : ViewModel() {
    var uiState by mutableStateOf(PaymentsUiState(items = sample()))
        private set

    fun onEvent(event: PaymentsUiEvent) {
        when (event) {
            is PaymentsUiEvent.OnFilterChanged -> uiState = uiState.copy(filter = event.value)
            PaymentsUiEvent.OnExport -> { /* TODO: export */ }
        }
    }
}

private fun sample(): List<PaymentItem> =
    listOf(
        PaymentItem("Jul 20, 2024", "ORD-20240720-001", "Credit Card", "$55.75"),
        PaymentItem("Jul 19, 2024", "ORD-20240719-002", "Cash", "$22.50"),
        PaymentItem("Jul 18, 2024", "ORD-20240718-003", "Credit Card", "$38.90"),
        PaymentItem("Jul 17, 2024", "ORD-20240717-004", "Mobile Payment", "$45.20"),
        PaymentItem("Jul 16, 2024", "ORD-20240716-005", "Cash", "$15.00"),
        PaymentItem("Jul 15, 2024", "ORD-20240715-006", "Credit Card", "$62.30"),
        PaymentItem("Jul 14, 2024", "ORD-20240714-007", "Mobile Payment", "$28.45"),
        PaymentItem("Jul 13, 2024", "ORD-20240713-008", "Cash", "$18.75"),
        PaymentItem("Jul 12, 2024", "ORD-20240712-009", "Credit Card", "$51.10"),
        PaymentItem("Jul 11, 2024", "ORD-20240711-010", "Mobile Payment", "$33.60"),
    )
