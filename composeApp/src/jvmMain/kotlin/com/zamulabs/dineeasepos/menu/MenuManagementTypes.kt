package com.zamulabs.dineeasepos.menu

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
    data object OnClickAddItem: MenuManagementUiEvent
    data class OnSearch(val query: String): MenuManagementUiEvent
    data class OnTabSelected(val tab: MenuTab): MenuManagementUiEvent
    data class OnToggleActive(val index: Int): MenuManagementUiEvent
    data class OnEdit(val index: Int): MenuManagementUiEvent
}
