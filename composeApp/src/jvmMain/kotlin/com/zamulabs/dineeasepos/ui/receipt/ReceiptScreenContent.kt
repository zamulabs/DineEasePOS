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
                        }
                    }
                }
            }
        }
    )
}
