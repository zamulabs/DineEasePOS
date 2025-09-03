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
package com.zamulabs.dineeasepos.ui.table.details

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable

@Immutable
 data class TableOrderItem(
    val name: String,
    val quantity: Int,
    val price: String,
    val total: String,
)

@Immutable
 data class TableDetailsUiState(
    val tableNumber: String = "Table 1",
    val capacity: Int = 4,
    val items: List<TableOrderItem> =
        listOf(
            TableOrderItem("Burger", 1, "$12.00", "$12.00"),
            TableOrderItem("Fries", 1, "$4.00", "$4.00"),
            TableOrderItem("Soda", 1, "$2.00", "$2.00"),
        ),
    val subtotal: String = "$18.00",
    val tax: String = "$1.44",
    val total: String = "$19.44",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)

sealed interface TableDetailsUiEvent {
    data object OnClickEditTable : TableDetailsUiEvent

    data object OnClickCreateOrder : TableDetailsUiEvent
}

sealed class TableDetailsUiEffect {
    data class ShowToast(val message: String) : TableDetailsUiEffect()
    data class ShowSnackBar(val message: String) : TableDetailsUiEffect()
    data object NavigateBack : TableDetailsUiEffect()
    data object NavigateToNewOrder : TableDetailsUiEffect()
    data object NavigateToEditTable : TableDetailsUiEffect()
}
