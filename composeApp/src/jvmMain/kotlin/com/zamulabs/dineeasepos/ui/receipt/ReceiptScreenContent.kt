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
        topBar = {
            BackBreadcrumb(
                parentLabel = "Orders",
                currentLabel = "Receipt",
                onBack = onBack,
            )
        },
        contentList = {
            item { Text("Order ${state.orderId}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline) }
            item { SectionTitle("Restaurant Information") }
            item { KeyValueRow("Restaurant Name", state.restaurantName) }
            item { KeyValueRow("Address", state.address) }
            item { KeyValueRow("Phone", state.phone) }

            item { SectionTitle("Order Information") }
            item { KeyValueRow("Order Date", state.orderDate) }
            item { KeyValueRow("Order Time", state.orderTime) }
            item { KeyValueRow("Order Type", state.orderType) }

            item { SectionTitle("Items") }
            item {
                AppDataTable(
                    columns = listOf(
                        com.seanproctor.datatable.DataColumn { Text("Item") },
                        com.seanproctor.datatable.DataColumn { Text("Quantity") },
                        com.seanproctor.datatable.DataColumn { Text("Price") },
                        com.seanproctor.datatable.DataColumn { Text("Total") },
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.items.forEach { item ->
                        row {
                            cell { Text(item.item) }
                            cell { Text(item.quantity.toString(), color = MaterialTheme.colorScheme.outline) }
                            cell { Text(item.price, color = MaterialTheme.colorScheme.outline) }
                            cell { Text(item.total, color = MaterialTheme.colorScheme.outline) }
                        }
                    }
                }
            }

            item { SectionTitle("Summary") }
            item { KeyValueRow("Subtotal", state.subtotal) }
            item { KeyValueRow("Tax", state.tax) }
            item { KeyValueRow("Total", state.total) }

            item { SectionTitle("Payment") }
            item { KeyValueRow("Payment Method", state.paymentMethod) }

            item { SectionTitle("Footer") }
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Text("Thank you for dining with us! We hope to see you again soon.")
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                    AppButton(onClick = { onEvent(ReceiptUiEvent.OnPrint) }){
                        Text("Print")
                    }
                    Spacer(Modifier.width(12.dp))
                    FilledTonalButton(onClick = { onEvent(ReceiptUiEvent.OnSavePdf) }){ Text("Save as PDF") }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                    FilledTonalButton(onClick = { onEvent(ReceiptUiEvent.OnEmail) }){ Text("Email") }
                }
            }
        }
    )
}

@Composable
private fun SectionTitle(title: String){
    Spacer(Modifier.height(12.dp))
    Text(title, style = MaterialTheme.typography.titleLarge)
}

@Composable
private fun KeyValueRow(key: String, value: String){
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)){
        Text(key, modifier = Modifier.width(160.dp), color = MaterialTheme.colorScheme.outline)
        Text(value)
    }
}
