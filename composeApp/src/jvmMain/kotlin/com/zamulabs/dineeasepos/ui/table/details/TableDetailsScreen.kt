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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.ui.navigation.Destinations
import com.zamulabs.dineeasepos.utils.ObserverAsEvent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun TableDetailsScreen(
    navController: NavController,
){
    val vm: TableDetailsViewModel = koinInject<TableDetailsViewModel>()
    val state by vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    ObserverAsEvent(flow = vm.uiEffect) { effect ->
        when (effect) {
            is TableDetailsUiEffect.ShowSnackBar -> {
                scope.launch { state.snackbarHostState.showSnackbar(effect.message) }
            }
            is TableDetailsUiEffect.ShowToast -> { /* TODO: desktop toast lib */ }
            TableDetailsUiEffect.NavigateBack -> navController.popBackStack()
            TableDetailsUiEffect.NavigateToNewOrder -> navController.navigate(Destinations.NewOrder)
            TableDetailsUiEffect.NavigateToEditTable -> navController.navigate(Destinations.AddTable)
        }
    }

    TableDetailsScreenContent(
        state = state,
        onEvent = vm::onEvent,
        onBack = { navController.popBackStack() }
    )
}
