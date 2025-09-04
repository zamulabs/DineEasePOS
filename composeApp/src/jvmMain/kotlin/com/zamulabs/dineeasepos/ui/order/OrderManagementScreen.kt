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
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.ui.components.SplitScreenScaffold
import com.zamulabs.dineeasepos.utils.ObserverAsEvent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun OrderManagementScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: OrderManagementViewModel = koinInject<OrderManagementViewModel>()
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.loadOrders()
    }

    ObserverAsEvent(viewModel.uiEffect) { effect ->
        when (effect) {
            is OrderManagementUiEffect.ShowSnackBar -> {
                scope.launch {
                    state.snackbarHostState.showSnackbar(effect.message)
                }
            }

            is OrderManagementUiEffect.ShowToast -> {
                // TODO: show desktop toast if available
            }

            OrderManagementUiEffect.NavigateBack -> navController.popBackStack()
        }
    }

    SplitScreenScaffold(
        mainRatio = 0.8f,
        sideRatio = 0.2f,
        main = {
            OrderManagementScreenContent(
                modifier = modifier,
                onEvent = { ev ->
                    when (ev) {
                        OrderManagementUiEvent.OnClickNewOrder -> {
                            // Navigate to New Order independent screen
                            com.zamulabs.dineeasepos.ui.navigation.Destinations.NewOrder.let { dest ->
                                navController.navigate(dest)
                            }
                        }

                        else -> viewModel.onEvent(ev)
                    }
                },
                onOrderClick = { oid ->
                    viewModel.onEvent(
                        OrderManagementUiEvent.OnClickViewDetails(
                            oid
                        )
                    )
                },
                state = state
            )
        },
        side = if (state.selectedOrderId != null) {
            {
                val detailsVm: com.zamulabs.dineeasepos.ui.order.details.OrderDetailsViewModel =
                    org.koin.compose.koinInject()
                val detailsState by detailsVm.uiState.collectAsState()

                androidx.compose.runtime.LaunchedEffect(state.selectedOrderId) {
                    detailsVm.loadOrderDetails(state.selectedOrderId)
                }

                com.zamulabs.dineeasepos.ui.order.details.OrderDetailsScreenContent(
                    state = detailsState,
                    onEvent = detailsVm::onEvent,
                    onBack = { viewModel.closeSidePane() }
                )
            }
        } else null // 🚀 No side pane until an order is clicked
    )

}
