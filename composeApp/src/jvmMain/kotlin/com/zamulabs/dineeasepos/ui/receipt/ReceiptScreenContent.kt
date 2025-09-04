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
package com.zamulabs.dineeasepos.ui.receipt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zamulabs.dineeasepos.ui.components.table.AppDataTable
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.BackBreadcrumb

@Composable
fun ReceiptScreenContent(
    state: ReceiptUiState,
    onEvent: (ReceiptUiEvent) -> Unit,
    onBack: () -> Unit,
) {
    AppScaffold(
        topBar = { com.zamulabs.dineeasepos.ui.components.ui.AppScreenTopBar(title = "Receipts") },
        contentList = {
            item {
                com.zamulabs.dineeasepos.ui.components.ui.AppTextField(
                    value = state.search,
                    onValueChange = { onEvent(ReceiptUiEvent.OnSearchChanged(it)) },
                    label = { Text("Search receipts") },
                    placeholder = { Text("Search by order id or receipt number") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                val filtered = state.items.filter { item ->
                    val q = state.search.trim().lowercase()
                    if (q.isEmpty()) true else (
                        item.orderId.lowercase().contains(q) || item.receiptNo.lowercase().contains(q)
                    )
                }
                AppDataTable(
                    columns = listOf(
                        com.seanproctor.datatable.DataColumn { Text("Receipt No") },
                        com.seanproctor.datatable.DataColumn { Text("Order ID") },
                        com.seanproctor.datatable.DataColumn { Text("Date") },
                        com.seanproctor.datatable.DataColumn { Text("Method") },
                        com.seanproctor.datatable.DataColumn { Text("Amount") },
                        com.seanproctor.datatable.DataColumn { Text("Actions") },
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    filtered.forEach { r ->
                        row {
                            cell { Text(r.receiptNo) }
                            cell { Text(r.orderId, color = MaterialTheme.colorScheme.outline) }
                            cell { Text(r.date, color = MaterialTheme.colorScheme.outline) }
                            cell { Text(r.method, color = MaterialTheme.colorScheme.outline) }
                            cell { Text(r.amount, color = MaterialTheme.colorScheme.outline) }
                            cell {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    AppButton(onClick = { onEvent(ReceiptUiEvent.OnReprint(r.orderId)) }) { Text("Reprint") }
                                }
                            }
                        }
                    }
                }
            }
            if (state.detail != null) {
                item { Spacer(Modifier.height(12.dp)) }
                item {
                    ReceiptDetailCard(detail = state.detail, onDismiss = { onEvent(ReceiptUiEvent.OnDismissDetail) }, onPrint = { onEvent(ReceiptUiEvent.OnPrint) })
                }
            }
        }
    )
}

@Composable
private fun ReceiptDetailCard(detail: ReceiptDetail, onDismiss: () -> Unit, onPrint: () -> Unit) {
    Column(Modifier.fillMaxWidth().padding(12.dp)) {
        Text("${'$'}{detail.restaurantName}", style = MaterialTheme.typography.titleLarge)
        Text(detail.address, color = MaterialTheme.colorScheme.outline)
        Text(detail.phone, color = MaterialTheme.colorScheme.outline)
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Order: ${'$'}{detail.orderId}")
            Text("${'$'}{detail.orderDate} ${'$'}{detail.orderTime}")
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Type: ${'$'}{detail.orderType}")
            Text("Method: ${'$'}{detail.paymentMethod}")
        }
        Spacer(Modifier.height(8.dp))
        AppDataTable(
            columns = listOf(
                com.seanproctor.datatable.DataColumn { Text("Item") },
                com.seanproctor.datatable.DataColumn { Text("Qty") },
                com.seanproctor.datatable.DataColumn { Text("Price") },
                com.seanproctor.datatable.DataColumn { Text("Total") },
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            detail.items.forEach { itx ->
                row {
                    cell { Text(itx.item) }
                    cell { Text(itx.quantity.toString(), color = MaterialTheme.colorScheme.outline) }
                    cell { Text(itx.price, color = MaterialTheme.colorScheme.outline) }
                    cell { Text(itx.total, color = MaterialTheme.colorScheme.outline) }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Subtotal", color = MaterialTheme.colorScheme.outline)
            Text(detail.subtotal)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Tax", color = MaterialTheme.colorScheme.outline)
            Text(detail.tax)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", color = MaterialTheme.colorScheme.outline)
            Text(detail.total)
        }
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            FilledTonalButton(onClick = onDismiss) { Text("Close") }
            Spacer(Modifier.width(8.dp))
            AppButton(onClick = onPrint) { Text("Print Receipt") }
        }
    }
}
