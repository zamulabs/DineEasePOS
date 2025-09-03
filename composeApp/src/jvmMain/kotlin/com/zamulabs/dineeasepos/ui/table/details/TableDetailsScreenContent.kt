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
package com.zamulabs.dineeasepos.ui.table.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.ui.components.table.AppDataTable
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.BackBreadcrumb

@Composable
fun TableDetailsScreenContent(
    state: TableDetailsUiState,
    onEvent: (TableDetailsUiEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
){
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
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
