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
    com.zamulabs.dineeasepos.utils.ObserverAsEvent(vm.uiEffect) { effect ->
        when (effect) {
            is TableManagementUiEffect.ShowSnackBar -> scope.launch { state.snackbarHostState.showSnackbar(effect.message) }
            is TableManagementUiEffect.ShowToast -> {}
            TableManagementUiEffect.NavigateBack -> navController.popBackStack()
        }
    }

    TableManagementScreenContent(state = state, onEvent = { ev ->
        when(ev){
            is TableManagementUiEvent.OnClickAddTable -> navController.navigate(Destinations.AddTable)
            is TableManagementUiEvent.OnClickViewDetails -> navController.navigate(Destinations.TableDetails)
            is TableManagementUiEvent.OnSearch -> { /* optional search handling in VM */ }
        }
        vm.onEvent(ev)
    })
}
