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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.utils.ObserverAsEvent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AddTableScreen(
    navController: NavController,
    vm: AddTableViewModel = koinInject<AddTableViewModel>()
){
    val state by vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    ObserverAsEvent(flow = vm.uiEffect) { effect ->
        when (effect) {
            is AddTableUiEffect.ShowSnackBar -> {
                scope.launch { state.snackbarHostState.showSnackbar(effect.message) }
            }
            is AddTableUiEffect.ShowToast -> {
                // TODO: add desktop toast if needed
            }
            AddTableUiEffect.NavigateBack -> navController.popBackStack()
        }
    }

    AddTableScreenContent(
        state = state,
        onEvent = vm::onEvent,
        onSave = { vm.onEvent(AddTableUiEvent.OnSave) },
        onCancel = { vm.onEvent(AddTableUiEvent.OnCancel) }
    )
}
