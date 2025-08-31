package com.zamulabs.dineeasepos.table.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.components.table.AppDataTable

@Composable
fun TableDetailsScreenContent(
    state: TableDetailsUiState,
    onEvent: (TableDetailsUiEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
){
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = {
            com.zamulabs.dineeasepos.components.ui.BackBreadcrumb(
                parentLabel = "Tables",
                currentLabel = state.tableNumber,
                onBack = onBack,
            )
        },
        contentList = {
            item { Spacer(Modifier.height(8.dp)) }
            item {
                Row(
                    Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(state.tableNumber, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
                        Text("Capacity: ${'$'}{state.capacity}", color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
            item { Text("Current Order", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) }
            item {
                AppDataTable(
                    columns = listOf(
                        DataColumn { Text("Item") },
                        DataColumn { Text("Quantity") },
                        DataColumn { Text("Price") },
                        DataColumn { Text("Total") },
                    ),
                    paginated = false,
                ){
                    state.items.forEach { item ->
                        row {
                            cell { Text(item.name) }
                            cell { Text(item.quantity.toString(), color = MaterialTheme.colorScheme.outline) }
                            cell { Text(item.price, color = MaterialTheme.colorScheme.outline) }
                            cell { Text(item.total, color = MaterialTheme.colorScheme.outline) }
                        }
                    }
                }
            }
            item { Text("Subtotal: ${'$'}{state.subtotal}", color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(vertical = 4.dp)) }
            item { Text("Tax: ${'$'}{state.tax}", color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(vertical = 4.dp)) }
            item { Text("Total: ${'$'}{state.total}", color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(vertical = 4.dp)) }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                    Button(onClick = { onEvent(TableDetailsUiEvent.OnClickEditTable) }, shape = MaterialTheme.shapes.extraLarge, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF264532))) {
                        Text("Edit Table", color = Color.White)
                    }
                    Spacer(Modifier.width(12.dp))
                    Button(onClick = { onEvent(TableDetailsUiEvent.OnClickCreateOrder) }, shape = MaterialTheme.shapes.extraLarge, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B))) {
                        Text("Create Order", color = Color(0xFF122118))
                    }
                }
            }
        }
    )
}
