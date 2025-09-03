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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppDropdown
import com.zamulabs.dineeasepos.ui.components.ui.AppFilterChip
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppTextField
import com.zamulabs.dineeasepos.ui.components.ui.BackBreadcrumb

@Composable
fun PaymentProcessingScreenContent(
    state: PaymentProcessingUiState,
    onEvent: (PaymentProcessingUiEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
){
    AppScaffold(
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
                parentLabel = "Orders",
                currentLabel = "Order ${'$'}{state.orderId}",
                onBack = onBack,
            )
        },
        contentList = {
        // Title provided by top bar breadcrumbs; avoid duplicate header
        item { Spacer(Modifier.height(8.dp)) }
        item { Text("Order Summary", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)) }
        item {
            Column(Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                SummaryRow("Subtotal", state.subtotal)
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SummaryRow("Tax", state.tax)
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SummaryRow("Total", state.total)
            }
        }
        item { Text("Payment Method", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)) }
        item {
            Row(Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)){
                AppFilterChip(selected = state.method==PaymentMethod.Cash, onClick = { onEvent(PaymentProcessingUiEvent.OnMethodSelected(PaymentMethod.Cash)) }, label = "Cash")
                AppFilterChip(selected = state.method==PaymentMethod.Online, onClick = { onEvent(PaymentProcessingUiEvent.OnMethodSelected(PaymentMethod.Online)) }, label = "Online")
            }
        }
        item {
            Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 8.dp)){
                AppTextField(
                    value = state.amountReceived,
                    onValueChange = { onEvent(PaymentProcessingUiEvent.OnAmountChanged(it)) },
                    placeholder = { Text("Amount Received") },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    singleLine = true,
                )
            }
        }
        item { Text("Change Due: ${state.changeDue}", color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(horizontal = 4.dp)) }
        item { Text("Online Payment", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)) }
        item {
            Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 8.dp)){
                AppDropdown(
                    label = "Gateway",
                    selected = state.onlineGateway,
                    items = listOf("Stripe", "PayPal", "Square"),
                    onSelected = { onEvent(PaymentProcessingUiEvent.OnOnlineGatewaySelected(it)) },
                    modifier = Modifier.fillMaxWidth(0.5f),
                )
            }
        }
        item { Text("Transaction Status: ${state.transactionStatus}", color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(horizontal = 4.dp)) }
        item {
            Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 12.dp)){
                AppButton(onClick = { onEvent(PaymentProcessingUiEvent.OnProcessPayment) }) { Text("Process Payment") }
            }
        }
    }
    )
}

@Composable
private fun SummaryRow(label: String, value: String){
    Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween){
        Text(label, color = MaterialTheme.colorScheme.outline)
        Text(value)
    }
}
