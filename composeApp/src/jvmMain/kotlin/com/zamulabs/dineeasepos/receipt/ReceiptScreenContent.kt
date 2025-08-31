package com.zamulabs.dineeasepos.receipt

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zamulabs.dineeasepos.components.table.AppDataTable

@Composable
fun ReceiptScreenContent(
    state: ReceiptUiState,
    onEvent: (ReceiptUiEvent) -> Unit,
    onBack: () -> Unit,
) {
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        topBar = {
            com.zamulabs.dineeasepos.components.ui.BackBreadcrumb(
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
                    com.zamulabs.dineeasepos.components.ui.AppButton(onClick = { onEvent(ReceiptUiEvent.OnPrint) }){
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
