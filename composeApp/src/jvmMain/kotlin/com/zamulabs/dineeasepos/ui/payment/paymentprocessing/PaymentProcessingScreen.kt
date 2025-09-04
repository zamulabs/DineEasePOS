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
package com.zamulabs.dineeasepos.ui.payment.paymentprocessing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.navigation.Destinations
import com.zamulabs.dineeasepos.utils.ObserverAsEvent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun PaymentProcessingScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val vm: PaymentProcessingViewModel = koinInject()
    val state by vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    ObserverAsEvent(vm.uiEffect) { effect ->
        when (effect) {
            is PaymentProcessingUiEffect.ShowSnackBar -> {
                scope.launch { state.snackbarHostState.showSnackbar(effect.message) }
            }
            is PaymentProcessingUiEffect.NavigateToReceipt -> {
                navController.navigate(Destinations.Receipt)
            }
            PaymentProcessingUiEffect.NavigateBack -> {
                navController.popBackStack()
            }
        }
    }

    com.zamulabs.dineeasepos.ui.components.SplitScreenScaffold(
        main = {
            PaymentProcessingScreenContent(
                state = state,
                onEvent = { ev -> vm.onEvent(ev) },
                onBack = { vm.onEvent(PaymentProcessingUiEvent.OnBack) },
                modifier = modifier
            )
        },
        side = {
            // Side pane per design: order items, totals, progress, and print
            PaymentProcessingSidePane(state = state)
        }
    )
}

@Composable
private fun PaymentProcessingSidePane(state: PaymentProcessingUiState) {
    // Minimal side pane matching design: list of sample items and totals from state, progress and print button
    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(16.dp)) {
        Text("Order ${state.orderId}")
        androidx.compose.foundation.layout.Spacer(Modifier.height(8.dp))
        // Placeholder items; in real app this would come from order details
        listOf(
            "2x Burger" to "$20.00",
            "1x Fries" to "$5.00",
            "1x Soda" to "$2.50",
        ).forEach { (label, price) ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(label, color = MaterialTheme.colorScheme.outline)
                Text(price)
            }
        }
        androidx.compose.material3.HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Subtotal", color = MaterialTheme.colorScheme.outline)
            Text(state.subtotal)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Tax", color = MaterialTheme.colorScheme.outline)
            Text(state.tax)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", color = MaterialTheme.colorScheme.outline)
            Text(state.total)
        }
        androidx.compose.foundation.layout.Spacer(Modifier.height(12.dp))
        Text("Payment Processing", style = MaterialTheme.typography.titleMedium)
        androidx.compose.foundation.layout.Spacer(Modifier.height(6.dp))
        // Simple progress representation: show 75% when Processing
        val progress = if (state.transactionStatus.equals("Processing", ignoreCase = true)) 0.75f else if (state.transactionStatus.equals("Completed", true)) 1f else 0.25f
        androidx.compose.material3.LinearProgressIndicator(progress = { progress })
        Text(
            when {
                progress >= 1f -> "Completed"
                progress >= 0.5f -> "Waiting for confirmation"
                else -> state.transactionStatus
            },
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 6.dp)
        )
        androidx.compose.foundation.layout.Spacer(Modifier.height(12.dp))
        AppButton(onClick = { /* TODO: printing */ }, modifier = Modifier.fillMaxWidth()) { Text("Print Receipt") }
    }
}
