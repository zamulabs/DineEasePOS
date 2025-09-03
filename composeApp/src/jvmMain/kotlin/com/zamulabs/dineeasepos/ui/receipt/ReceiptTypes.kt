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
package com.zamulabs.dineeasepos.ui.receipt

import androidx.compose.runtime.Immutable

@Immutable
data class ReceiptListItem(
    val receiptNo: String,
    val orderId: String,
    val date: String,
    val method: String,
    val amount: String,
)

@Immutable
data class ReceiptUiState(
    val items: List<ReceiptListItem> = emptyList(),
    val search: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val snackbarHostState: androidx.compose.material3.SnackbarHostState = androidx.compose.material3.SnackbarHostState(),
)

sealed interface ReceiptUiEvent {
    data class OnSearchChanged(val value: String) : ReceiptUiEvent
    data object OnExport : ReceiptUiEvent
}
