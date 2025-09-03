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
package com.zamulabs.dineeasepos.ui.payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import com.zamulabs.dineeasepos.utils.ObserverAsEvent
import kotlinx.coroutines.launch

@Composable
fun PaymentsScreen(
    modifier: Modifier = Modifier,
    viewModel: PaymentsViewModel = koinInject<PaymentsViewModel>()
){
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel){
        viewModel.loadPayments()
    }

    ObserverAsEvent(viewModel.uiEffect){ effect ->
        when(effect){
            is PaymentsUiEffect.ShowSnackBar -> scope.launch { state.snackbarHostState.showSnackbar(effect.message) }
            is PaymentsUiEffect.ShowToast -> {}
            PaymentsUiEffect.NavigateBack -> {}
        }
    }

    PaymentsScreenContent(
        state = state,
        onEvent = { ev -> viewModel.onEvent(ev) },
        modifier = modifier,
    )
}
