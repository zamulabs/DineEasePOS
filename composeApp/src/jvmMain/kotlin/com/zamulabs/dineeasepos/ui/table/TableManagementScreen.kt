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
package com.zamulabs.dineeasepos.ui.table

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.ui.navigation.Destinations
import org.koin.compose.koinInject
import kotlinx.coroutines.launch

@Composable
fun TableManagementScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    vm: TableManagementViewModel = koinInject<TableManagementViewModel>(),
) {
    val state by vm.uiState.collectAsState()

    LaunchedEffect(vm) {
        vm.loadTables()
    }

    val scope = rememberCoroutineScope()

    // Inject add-table VM for side pane usage and observe its effects
    val addVm =
        org.koin.compose.koinInject<com.zamulabs.dineeasepos.ui.table.addtable.AddTableViewModel>()

    com.zamulabs.dineeasepos.utils.ObserverAsEvent(vm.uiEffect) { effect ->
        when (effect) {
            is TableManagementUiEffect.ShowSnackBar -> scope.launch {
                state.snackbarHostState.showSnackbar(
                    effect.message
                )
            }

            is TableManagementUiEffect.ShowToast -> {}
            TableManagementUiEffect.NavigateBack -> navController.popBackStack()
        }
    }

    com.zamulabs.dineeasepos.utils.ObserverAsEvent(addVm.uiEffect) { effect ->
        when (effect) {
            is com.zamulabs.dineeasepos.ui.table.addtable.AddTableUiEffect.ShowSnackBar ->
                scope.launch { state.snackbarHostState.showSnackbar(effect.message) }

            is com.zamulabs.dineeasepos.ui.table.addtable.AddTableUiEffect.ShowToast -> {}

            com.zamulabs.dineeasepos.ui.table.addtable.AddTableUiEffect.NavigateBack -> {
                // Close side pane and refresh list after successful add
                vm.updateUiState { copy(showAddTable = false) }
                vm.loadTables()
            }
        }
    }

    val showSidePane = state.showAddTable || state.selectedTable != null

    com.zamulabs.dineeasepos.ui.components.SplitScreenScaffold(
        modifier = modifier,
        main = {
            TableManagementScreenContent(
                state = state,
                onEvent = { ev ->
                    when (ev) {
                        is TableManagementUiEvent.OnClickAddTable -> vm.onEvent(ev)
                        is TableManagementUiEvent.OnClickViewDetails -> vm.onEvent(ev)
                        is TableManagementUiEvent.OnSearch -> vm.onEvent(ev)
                    }
                }
            )
        },
        side = if (showSidePane) {
            {
                when {
                    state.showAddTable -> {
                        val addState by addVm.uiState.collectAsState()
                        com.zamulabs.dineeasepos.ui.table.addtable.AddTableScreenContent(
                            state = addState,
                            onEvent = addVm::onEvent,
                            onSave = { addVm.onEvent(com.zamulabs.dineeasepos.ui.table.addtable.AddTableUiEvent.OnSave) },
                            onCancel = { vm.updateUiState { copy(showAddTable = false) } }
                        )
                    }

                    state.selectedTable != null -> {
                        val table = state.selectedTable!!
                        val detailsState =
                            com.zamulabs.dineeasepos.ui.table.details.TableDetailsUiState(
                                tableNumber = table.number,
                                capacity = table.capacity,
                            )
                        com.zamulabs.dineeasepos.ui.table.details.TableDetailsScreenContent(
                            state = detailsState,
                            onEvent = { ev ->
                                when (ev) {
                                    com.zamulabs.dineeasepos.ui.table.details.TableDetailsUiEvent.OnClickCreateOrder ->
                                        navController.navigate(Destinations.NewOrder)

                                    com.zamulabs.dineeasepos.ui.table.details.TableDetailsUiEvent.OnClickEditTable ->
                                        navController.navigate(Destinations.AddTable)
                                }
                            },
                            onBack = { vm.updateUiState { copy(selectedTable = null) } }
                        )
                    }
                }
            }
        } else null // 🔑 hide side pane when no selection and not adding
    )
}
