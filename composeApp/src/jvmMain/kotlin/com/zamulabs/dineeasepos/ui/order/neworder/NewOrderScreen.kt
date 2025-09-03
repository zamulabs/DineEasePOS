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
package com.zamulabs.dineeasepos.ui.order.neworder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.ui.navigation.Destinations
import com.zamulabs.dineeasepos.utils.ObserverAsEvent
import org.koin.compose.koinInject

@Composable
fun NewOrderScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NewOrderViewModel = koinInject<NewOrderViewModel>(),
){
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.loadData()
    }

    ObserverAsEvent(flow = viewModel.uiEffect) { effect ->
        when (effect) {
            is NewOrderUiEffect.ShowSnackBar -> {
                // ScreenContent owns the SnackbarHostState; triggering via state
                // If needed, we could forward messages through a stateful host; kept simple here
            }
            NewOrderUiEffect.NavigateBack -> navController.popBackStack()
        }
    }

    NewOrderScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onPlaceOrder = { navController.navigate(Destinations.PaymentProcessing) },
        onBack = { navController.popBackStack() },
        modifier = modifier
    )
}
