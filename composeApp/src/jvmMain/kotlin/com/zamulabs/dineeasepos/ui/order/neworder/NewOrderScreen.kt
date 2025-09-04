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
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
            }
            NewOrderUiEffect.NavigateBack -> navController.popBackStack()
        }
    }

    // Inside New Order, we show a split: main = NewOrder; side = Payment Processing
    val payVm: com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentProcessingViewModel = org.koin.compose.koinInject()
    val payState by payVm.uiState.collectAsState()
    var showPayment by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    com.zamulabs.dineeasepos.utils.ObserverAsEvent(payVm.uiEffect) { effect ->
        when (effect) {
            is com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentProcessingUiEffect.ShowSnackBar -> {
                // SnackbarHost is inside PaymentProcessing content; skipping here
            }
            is com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentProcessingUiEffect.NavigateToReceipt -> {
                // Navigate to Receipt screen on completion from side pane context
                com.zamulabs.dineeasepos.ui.navigation.Destinations.Receipt.let { dest ->
                    navController.navigate(dest)
                }
            }
            com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentProcessingUiEffect.NavigateBack -> {
                showPayment = false
            }
        }
    }

    com.zamulabs.dineeasepos.ui.components.SplitScreenScaffold(
        main = {
            NewOrderScreenContent(
                state = state,
                onEvent = viewModel::onEvent,
                onPlaceOrder = {
                    // Push current order totals into payment VM before opening pane
                    val orderId = "#" + (state.selectedTable.ifBlank { "Takeaway" })
                    payVm.setOrderTotals(orderId = orderId, subtotal = state.subtotal, tax = state.tax, total = state.total)
                    showPayment = true
                },
                onBack = { navController.popBackStack() },
                modifier = modifier
            )
        },
        side = {
            if (showPayment) {
                com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentProcessingScreenContent(
                    state = payState,
                    onEvent = payVm::onEvent,
                    onBack = { showPayment = false }
                )
            } else {
                androidx.compose.material3.Text(
                    "Proceed to payment to open processing pane",
                    modifier = androidx.compose.ui.Modifier.padding(16.dp)
                )
            }
        }
    )
}
