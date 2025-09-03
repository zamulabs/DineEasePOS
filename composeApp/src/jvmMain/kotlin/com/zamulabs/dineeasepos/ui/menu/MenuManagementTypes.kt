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
package com.zamulabs.dineeasepos.ui.menu

import androidx.compose.runtime.Immutable

@Immutable
data class MenuItem(
    val name: String,
    val category: String,
    val price: String,
    val active: Boolean,
)

@Immutable
data class MenuManagementUiState(
    val items: List<MenuItem> = emptyList(),
    val searchString: String = "",
    val selectedTab: MenuTab = MenuTab.All,
)

enum class MenuTab { All, Active, Inactive }

sealed interface MenuManagementUiEvent {
    data object OnClickAddItem : MenuManagementUiEvent

    data class OnSearch(
        val query: String,
    ) : MenuManagementUiEvent

    data class OnTabSelected(
        val tab: MenuTab,
    ) : MenuManagementUiEvent

    data class OnToggleActive(
        val index: Int,
    ) : MenuManagementUiEvent

    data class OnEdit(
        val index: Int,
    ) : MenuManagementUiEvent
}
