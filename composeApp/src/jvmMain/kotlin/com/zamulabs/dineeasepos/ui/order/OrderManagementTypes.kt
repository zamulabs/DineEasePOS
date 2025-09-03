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

import androidx.compose.runtime.Immutable

@Immutable
data class Order(
    val id: String,
    val table: String,
    val status: OrderStatus,
    val total: String,
    val time: String,
)

enum class OrderStatus { Open, Completed }

@Immutable
data class OrderManagementUiState(
    val orders: List<Order> = emptyList(),
    val searchString: String = "",
    val selectedTab: OrderTab = OrderTab.All,
)

enum class OrderTab { All, Open, Completed }

sealed interface OrderManagementUiEvent {
    data object OnClickNewOrder : OrderManagementUiEvent

    data class OnSearch(
        val query: String,
    ) : OrderManagementUiEvent

    data class OnTabSelected(
        val tab: OrderTab,
    ) : OrderManagementUiEvent

    data class OnClickViewDetails(
        val orderId: String,
    ) : OrderManagementUiEvent
}

sealed interface OrderManagementUiEffect {
    data class ShowToast(
        val message: String,
    ) : OrderManagementUiEffect
}
