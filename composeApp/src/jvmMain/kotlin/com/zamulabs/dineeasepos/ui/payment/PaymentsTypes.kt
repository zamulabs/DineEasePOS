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

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable

@Immutable
data class PaymentItem(
    val date: String,
    val orderId: String,
    val method: String,
    val amount: String,
    val status: String = "Completed",
)

@Immutable
data class PaymentsUiState(
    val items: List<PaymentItem> = emptyList(),
    val filter: String = "",
    val isLoadingPayments: Boolean = false,
    val errorLoadingPayments: String? = null,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)

sealed interface PaymentsUiEvent {
    data class OnFilterChanged(
        val value: String,
    ) : PaymentsUiEvent

    data object OnExport : PaymentsUiEvent
}

sealed class PaymentsUiEffect {
    data class ShowToast(val message: String) : PaymentsUiEffect()
    data class ShowSnackBar(val message: String) : PaymentsUiEffect()
    data object NavigateBack : PaymentsUiEffect()
}
