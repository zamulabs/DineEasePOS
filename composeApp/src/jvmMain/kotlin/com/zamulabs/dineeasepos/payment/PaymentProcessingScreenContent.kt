package com.zamulabs.dineeasepos.payment

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
fun PaymentProcessingScreenContent(
    state: PaymentProcessingUiState,
    onEvent: (PaymentProcessingUiEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
){
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
        // Title provided by top bar breadcrumbs; avoid duplicate header
        item { Spacer(Modifier.height(8.dp)) }
        item { Text("Order Summary", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)) }
        item {
            Column(Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                SummaryRow("Subtotal", state.subtotal)
                Divider(color = MaterialTheme.colorScheme.outlineVariant)
                SummaryRow("Tax", state.tax)
                Divider(color = MaterialTheme.colorScheme.outlineVariant)
                SummaryRow("Total", state.total)
            }
        }
        item { Text("Payment Method", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)) }
        item {
            Row(Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)){
                com.zamulabs.dineeasepos.components.ui.AppFilterChip(selected = state.method==PaymentMethod.Cash, onClick = { onEvent(PaymentProcessingUiEvent.OnMethodSelected(PaymentMethod.Cash)) }, label = "Cash")
                com.zamulabs.dineeasepos.components.ui.AppFilterChip(selected = state.method==PaymentMethod.Online, onClick = { onEvent(PaymentProcessingUiEvent.OnMethodSelected(PaymentMethod.Online)) }, label = "Online")
            }
        }
        item {
            Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 8.dp)){
                com.zamulabs.dineeasepos.components.ui.AppTextField(
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
                com.zamulabs.dineeasepos.components.ui.AppDropdown(
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
                com.zamulabs.dineeasepos.components.ui.AppButton(onClick = { onEvent(PaymentProcessingUiEvent.OnProcessPayment) }) { Text("Process Payment") }
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
