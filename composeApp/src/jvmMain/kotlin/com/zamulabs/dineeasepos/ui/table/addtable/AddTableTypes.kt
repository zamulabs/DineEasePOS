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
package com.zamulabs.dineeasepos.ui.table.addtable

import androidx.compose.runtime.Immutable

@Immutable
data class AddTableUiState(
    val tableNumber: String = "",
    val tableName: String = "",
    val capacity: String = "",
    val location: String = "",
    val locations: List<String> = listOf("Select location", "Main Hall", "Patio", "VIP"),
    val loading: Boolean = false,
    val error: String? = null,
)

sealed interface AddTableUiEvent {
    data class OnTableNumberChanged(
        val value: String,
    ) : AddTableUiEvent

    data class OnTableNameChanged(
        val value: String,
    ) : AddTableUiEvent

    data class OnCapacityChanged(
        val value: String,
    ) : AddTableUiEvent

    data class OnLocationChanged(
        val value: String,
    ) : AddTableUiEvent

    data object OnCancel : AddTableUiEvent

    data object OnSave : AddTableUiEvent
}
