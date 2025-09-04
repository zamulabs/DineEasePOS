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
     val detail: ReceiptDetail? = null,
     val snackbarHostState: androidx.compose.material3.SnackbarHostState = androidx.compose.material3.SnackbarHostState(),
 )
 
 @Immutable
 data class ReceiptDetail(
     val orderId: String,
     val restaurantName: String,
     val address: String,
     val phone: String,
     val orderDate: String,
     val orderTime: String,
     val orderType: String,
     val items: List<ReceiptDetailItem>,
     val subtotal: String,
     val tax: String,
     val total: String,
     val paymentMethod: String,
 )
 
 @Immutable
 data class ReceiptDetailItem(
     val item: String,
     val quantity: Int,
     val price: String,
     val total: String,
 )
 
 sealed interface ReceiptUiEvent {
     data class OnSearchChanged(val value: String) : ReceiptUiEvent
     data object OnExport : ReceiptUiEvent
     data class OnReprint(val orderId: String) : ReceiptUiEvent
     data object OnDismissDetail : ReceiptUiEvent
     data object OnPrint : ReceiptUiEvent
 }
