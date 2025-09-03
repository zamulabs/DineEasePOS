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
        item { SectionTitle("Order Information") }
        item { GridRow("Customer", state.customer) }
        item { GridRow("Table", state.table) }
        item { GridRow("Server", state.server) }
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
                        cell {
                            Text(
                                item.quantity.toString(),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        cell { Text(item.price, color = MaterialTheme.colorScheme.outline) }
                    }
                }
            }
        }
        item { SectionTitle("Order Total") }
        item { GridRow("Subtotal", state.subtotal) }
        item { GridRow("Tax", state.tax) }
        item { GridRow("Total", state.total) }
        item { SectionTitle("Order Status") }
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text("Status", style = MaterialTheme.typography.titleMedium)
                    Text(state.orderStatus, color = MaterialTheme.colorScheme.outline)
                }
                Text("Confirmed")
            }
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                ActionButton("Mark Preparing") { onEvent(OrderDetailsUiEvent.MarkPreparing) }
                Spacer(Modifier.width(8.dp))
                ActionButton("Mark Ready") { onEvent(OrderDetailsUiEvent.MarkReady) }
                Spacer(Modifier.width(8.dp))
                ActionButton("Complete") { onEvent(OrderDetailsUiEvent.Complete) }
                Spacer(Modifier.width(8.dp))
                ActionButton("Cancel") { onEvent(OrderDetailsUiEvent.Cancel) }
            }
        }
        item { SectionTitle("Payment") }
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text("Status"); Text(
                    state.paymentStatus,
                    color = MaterialTheme.colorScheme.outline
                )
                }
                Text(state.paymentStatus)
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text("Method"); Text(
                    state.paymentMethod,
                    color = MaterialTheme.colorScheme.outline
                )
                }
                Text(state.paymentMethod)
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
