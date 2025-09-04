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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.ui.navigation.Destinations
import com.zamulabs.dineeasepos.utils.ObserverAsEvent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun MenuManagementScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MenuManagementViewModel = koinInject<MenuManagementViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.getMenuItems()
    }

    // Observe menu VM effects
    ObserverAsEvent(viewModel.uiEffect) { effect ->
        when (effect) {
            is MenuManagementUiEffect.ShowSnackBar -> {
                scope.launch {
                    uiState.snackbarHostState.showSnackbar(message = effect.message)
                }
            }
            is MenuManagementUiEffect.ShowToast -> { /* TODO: desktop toast */ }
            MenuManagementUiEffect.NavigateBack -> navController.popBackStack()
        }
    }

    // Observe AddMenu side-pane VM effects to close pane and refresh
    val addVm = org.koin.compose.koinInject<com.zamulabs.dineeasepos.ui.menu.addmenu.AddMenuItemViewModel>()
    ObserverAsEvent(addVm.uiEffect) { effect ->
        when (effect) {
            is com.zamulabs.dineeasepos.ui.menu.addmenu.AddMenuItemUiEffect.ShowSnackBar -> scope.launch { uiState.snackbarHostState.showSnackbar(effect.message) }
            is com.zamulabs.dineeasepos.ui.menu.addmenu.AddMenuItemUiEffect.ShowToast -> { }
            com.zamulabs.dineeasepos.ui.menu.addmenu.AddMenuItemUiEffect.NavigateBack -> {
                // Close side pane and refresh
                viewModel.updateUiState { copy(showAddMenu = false) }
                viewModel.getMenuItems()
            }
        }
    }

    // Use SplitScreenScaffold to show main list and a side pane for details or add-menu
    com.zamulabs.dineeasepos.ui.components.SplitScreenScaffold(
        main = {
            MenuManagementScreenContent(
                state = uiState,
                onEvent = { ev ->
                    when (ev) {
                        is MenuManagementUiEvent.OnClickAddItem -> viewModel.updateUiState { copy(showAddMenu = true) }
                        is MenuManagementUiEvent.OnClickViewDetails -> viewModel.onEvent(ev)
                        is MenuManagementUiEvent.OnSearch -> viewModel.onEvent(ev)
                        is MenuManagementUiEvent.OnTabSelected -> viewModel.onEvent(ev)
                        is MenuManagementUiEvent.OnToggleActive -> viewModel.onEvent(ev)
                        is MenuManagementUiEvent.OnEdit -> viewModel.onEvent(ev)
                        is MenuManagementUiEvent.OnDelete -> viewModel.onEvent(ev)
                    }
                },
                modifier = modifier
            )
        },
        side = {
            val showAdd = uiState.showAddMenu
            if (showAdd) {
                val addState by addVm.uiState.collectAsState()
                // Prefill edit form when selection changes and pane is open for edit
                androidx.compose.runtime.LaunchedEffect(uiState.selectedItem, uiState.showAddMenu) {
                    val sel = uiState.selectedItem
                    if (uiState.showAddMenu && sel != null) {
                        addVm.setEditing(sel)
                    }
                }
                com.zamulabs.dineeasepos.ui.menu.addmenu.AddMenuItemScreenContent(
                    state = addState,
                    onEvent = addVm::onEvent,
                    onSave = { addVm.onEvent(com.zamulabs.dineeasepos.ui.menu.addmenu.AddMenuItemUiEvent.OnSave) },
                    onCancel = { viewModel.updateUiState { copy(showAddMenu = false) } }
                )
            } else {
                // Render scaffolded details content in side pane
                run {
                    val detailsVm = org.koin.compose.koinInject<com.zamulabs.dineeasepos.ui.menu.details.MenuDetailsViewModel>()
                    val detailsState by detailsVm.uiState.collectAsState()
                    // Push selected item into details state when changed
                    androidx.compose.runtime.LaunchedEffect(uiState.selectedItem) {
                        val sel = uiState.selectedItem
                        if (sel != null) {
                            detailsVm.updateUiState {
                                copy(
                                    name = sel.name,
                                    category = sel.category,
                                    price = sel.price,
                                    active = sel.active,
                                )
                            }
                        }
                    }
                    com.zamulabs.dineeasepos.ui.menu.details.MenuDetailsScreenContent(
                        state = detailsState,
                        onEvent = { ev ->
                            when (ev) {
                                com.zamulabs.dineeasepos.ui.menu.details.MenuDetailsUiEvent.Edit -> { /* future edit */ }
                                com.zamulabs.dineeasepos.ui.menu.details.MenuDetailsUiEvent.ToggleActive -> {
                                    uiState.selectedItem?.let { viewModel.onEvent(MenuManagementUiEvent.OnToggleActive(it.name)) }
                                    detailsVm.onEvent(ev)
                                }
                                com.zamulabs.dineeasepos.ui.menu.details.MenuDetailsUiEvent.Delete -> { /* future delete */ }
                            }
                        },
                        onBack = { /* side pane has no back; ignore */ }
                    )
                }
            }
        }
    )
}
