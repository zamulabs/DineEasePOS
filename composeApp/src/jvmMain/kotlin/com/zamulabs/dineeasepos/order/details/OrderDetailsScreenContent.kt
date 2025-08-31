package com.zamulabs.dineeasepos.order.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.components.table.AppDataTable

@Composable
fun OrderDetailsScreenContent(
    state: OrderDetailsUiState,
    onEvent: (OrderDetailsUiEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = {
            com.zamulabs.dineeasepos.components.ui.BackBreadcrumb(
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
    com.zamulabs.dineeasepos.components.ui.AppButton(onClick = onClick) { Text(text) }
}
