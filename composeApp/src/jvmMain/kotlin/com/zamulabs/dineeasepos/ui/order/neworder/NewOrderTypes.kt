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
package com.zamulabs.dineeasepos.ui.order.neworder

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable

@Immutable
data class NewOrderUiState(
    val tables: List<String> = listOf("Select Table"),
    val selectedTable: String = "Select Table",
    val searchString: String = "",
    val categories: List<String> = listOf("Appetizers", "Main Courses", "Desserts", "Drinks", "Specials"),
    val selectedCategoryIndex: Int = 0,

    val isLoadingMenuItems: Boolean = false,
    val errorLoadingMenuItems: String? = null,
    val isLoadingTables: Boolean = false,
    val errorLoadingTables: String? = null,

    val items: List<MenuItem> = emptyList(),
    val cart: List<CartItem> = emptyList(),
    val notes: String = "",
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
) {
    val subtotal: Double get() = cart.sumOf { it.quantity * it.price }
    val tax: Double get() = (subtotal * 0.08).let { Math.round(it * 100.0) / 100.0 }
    val total: Double get() = Math.round((subtotal + tax) * 100.0) / 100.0
}

@Immutable
data class MenuItem(
    val title: String,
    val description: String,
    val imageUrl: String,
)

@Immutable
data class CartItem(
    val title: String,
    val quantity: Int,
    val price: Double,
)

sealed interface NewOrderUiEvent{
    data class OnSearch(val query: String): NewOrderUiEvent
    data class OnTableSelected(val table: String): NewOrderUiEvent
    data class OnCategorySelected(val index: Int): NewOrderUiEvent
    data class OnNotesChanged(val notes: String): NewOrderUiEvent
    data class OnAddToCart(val title: String): NewOrderUiEvent
    data class OnIncQty(val title: String): NewOrderUiEvent
    data class OnDecQty(val title: String): NewOrderUiEvent
    data class OnRemoveItem(val title: String): NewOrderUiEvent
    data object OnPlaceOrder: NewOrderUiEvent
}

sealed class NewOrderUiEffect {
    data class ShowSnackBar(val message: String) : NewOrderUiEffect()
    data object NavigateBack : NewOrderUiEffect()
}
