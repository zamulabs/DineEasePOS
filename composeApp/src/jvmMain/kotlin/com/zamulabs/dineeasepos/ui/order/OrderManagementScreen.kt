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
package com.zamulabs.dineeasepos.ui.order

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
fun OrderManagementScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: OrderManagementViewModel = koinInject<OrderManagementViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.loadOrders()
    }

    ObserverAsEvent(viewModel.uiEffect) { effect ->
        when (effect) {
            is OrderManagementUiEffect.ShowSnackBar -> {
                scope.launch {
                    uiState.snackbarHostState.showSnackbar(effect.message)
                }
            }
            is OrderManagementUiEffect.ShowToast -> {
                // TODO: show desktop toast if available
            }
            OrderManagementUiEffect.NavigateBack -> navController.popBackStack()
        }
    }

    OrderManagementScreenContent(
        modifier = modifier,
        onEvent = {
            if (it is OrderManagementUiEvent.OnClickNewOrder) {
                navController.navigate(Destinations.NewOrder)
            }
            if (it is OrderManagementUiEvent.OnClickViewDetails) {
                navController.navigate(Destinations.OrderDetails)
            }
            viewModel.onEvent(it)
        },
        onOrderClick = { navController.navigate(Destinations.OrderDetails) },
        state = uiState
    )
}
