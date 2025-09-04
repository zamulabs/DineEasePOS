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
package com.zamulabs.dineeasepos.ui.order.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.ui.components.table.AppDataTable
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.BackBreadcrumb

@Composable
fun OrderDetailsScreenContent(
    state: OrderDetailsUiState,
    onEvent: (OrderDetailsUiEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
                parentLabel = "Orders",
                currentLabel = "Order ${'$'}{state.orderId}",
                onBack = onBack,
            )
        },
        contentList = {
        item { Spacer(Modifier.height(8.dp)) }
        // Title moved to top bar; avoid duplicate header
        item { Text("Placed on ${state.placedOn}", color = MaterialTheme.colorScheme.outline) }
        item { SectionTitle("Order Items") }
        item {
            AppDataTable(
                columns = listOf(
                    DataColumn { Text("Item") },
                    DataColumn { Text("Quantity") },
                    DataColumn { Text("Price") },
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                state.items.forEach { item ->
                    row {
                        cell { Text(item.name) }
                        cell { Text(item.quantity.toString(), color = MaterialTheme.colorScheme.outline) }
                        cell { Text(item.price, color = MaterialTheme.colorScheme.outline) }
                    }
                }
            }
        }
        item { SectionTitle("Payments") }
        item {
            AppDataTable(
                columns = listOf(
                    DataColumn { Text("Method") },
                    DataColumn { Text("Amount") },
                    DataColumn { Text("Status") },
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Placeholder rows reflecting design; replace with real data when available
                row {
                    cell { Text("Credit Card") }
                    cell { Text("$20.00", color = MaterialTheme.colorScheme.outline) }
                    cell { Text("Completed") }
                }
                row {
                    cell { Text("Cash") }
                    cell { Text("$12.47", color = MaterialTheme.colorScheme.outline) }
                    cell { Text("Pending") }
                }
            }
        }
    }
    )
}

@Composable
fun OrderDetailsSidePane(
    state: OrderDetailsUiState,
    onEvent: (OrderDetailsUiEvent) -> Unit,
    onBack: () -> Unit,
) {
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        topBar = { /* side pane has no breadcrumb; keeps compact */ },
        contentList = {
            item { SectionTitle("Order Status") }
            item {
                Row(Modifier.fillMaxWidth()) {
                    // Simple status dropdown stub to match design interaction
                    com.zamulabs.dineeasepos.ui.components.ui.AppDropdown(
                        label = "",
                        selected = state.orderStatus,
                        items = listOf("Placed","Preparing","Ready","Completed","Cancelled"),
                        onSelected = { /* TODO: send status update event when backend ready */ },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item { SectionTitle("Order Summary") }
            item { GridRow("Subtotal", state.subtotal) }
            item { GridRow("Tax", state.tax) }
            item { GridRow("Total", state.total) }
            item { SectionTitle("Payment Summary") }
            item { GridRow("Paid", "$20.00") }
            item { GridRow("Remaining", "$15.72") }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    ActionButton("Process Payment") { onEvent(OrderDetailsUiEvent.MarkPreparing) }
                }
            }
            item {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ActionButton("Generate Receipt") { onEvent(OrderDetailsUiEvent.GenerateReceipt) }
                        Spacer(Modifier.width(8.dp))
                        ActionButton("Mark as Completed") { onEvent(OrderDetailsUiEvent.Complete) }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row { ActionButton("Cancel Order") { onEvent(OrderDetailsUiEvent.Cancel) } }
                }
            }
        }
    )
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
    )
}

@Composable
private fun GridRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Box(Modifier.width(180.dp)) { Text(label, color = MaterialTheme.colorScheme.outline) }
        Text(value)
    }
}

@Composable
private fun ActionButton(text: String, onClick: () -> Unit) {
    AppButton(onClick = onClick) { Text(text) }
}
