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
package com.zamulabs.dineeasepos.ui.order.neworder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppDropdown
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppTextField
import com.zamulabs.dineeasepos.ui.components.ui.BackBreadcrumb

// 🟢 Order Menu Pane
@Composable
fun OrderMenuPane(
    state: NewOrderUiState,
    onEvent: (NewOrderUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
                parentLabel = "Menu",
            )
        },
        contentHorizontalPadding = 24.dp,
    )
    {
        Column {
            Spacer(Modifier.height(8.dp))
            SearchField(state.searchString) { onEvent(NewOrderUiEvent.OnSearch(it)) }
            Spacer(Modifier.height(12.dp))
            CategoryTabs(state) { onEvent(NewOrderUiEvent.OnCategorySelected(it)) }
            Spacer(Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 158.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                items(state.items) { item ->
                    Column(Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF223A2C))
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            item.title,
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            item.description,
                            color = Color(0xFF96C5A9),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(8.dp))
                        AppButton(
                            onClick = { onEvent(NewOrderUiEvent.OnAddToCart(item.title)) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B))
                        ) {
                            Text("Add", color = Color(0xFF122118))
                        }
                    }
                }
            }
        }
    }
}

// 🟢 Order Summary Pane
@Composable
fun OrderSummaryPane(
    state: NewOrderUiState,
    onNotesChanged: (String) -> Unit,
    onPlaceOrder: () -> Unit,
    onEvent: (NewOrderUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
                parentLabel = "Table",
            )
        },
        contentHorizontalPadding = 24.dp,
    )
    {
        Column {
            Spacer(Modifier.height(8.dp))
            AppDropdown(
                label = "Table",
                items = state.tables,
                selected = state.selectedTable,
                onSelected = {
                    onEvent(NewOrderUiEvent.OnTableSelected(it))
                }
            )
            Spacer(Modifier.height(12.dp))

            Text(
                "Order Cart",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(8.dp))

            state.cart.forEach { item ->
                CartItemRow(
                    item = item,
                    onEvent = onEvent,
                )
            }

            Spacer(Modifier.height(8.dp))
            AppTextField(
                value = state.notes,
                onValueChange = onNotesChanged,
                placeholder = { Text("Special Instructions", color = Color(0xFF96C5A9)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
            )
            Spacer(Modifier.height(8.dp))

            Text(
                "Order Summary",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Column(Modifier.padding(horizontal = 4.dp)) {
                SummaryRow("Subtotal", state.subtotal)
                SummaryRow("Tax", state.tax)
                SummaryRow("Total", state.total)
            }
            Spacer(Modifier.height(12.dp))

            AppButton(
                onClick = onPlaceOrder,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B))
            ) {
                Text("Place Order", color = Color(0xFF122118), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onEvent: (NewOrderUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ─── Top: Item title + quantity controls ───────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Qty: ${item.quantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { onEvent(NewOrderUiEvent.OnDecQty(item.title)) },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease")
                    }
                    IconButton(
                        onClick = { onEvent(NewOrderUiEvent.OnIncQty(item.title)) },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase")
                    }
                }
            }

            // ─── Bottom: Price + Remove ───────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$" + String.format("%.2f", item.price),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                TextButton(
                    onClick = { onEvent(NewOrderUiEvent.OnRemoveItem(item.title)) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Remove")
                }
            }
        }
    }
}


@Composable
private fun SearchField(value: String, onChanged: (String) -> Unit) {
    AppTextField(
        value = value,
        onValueChange = onChanged,
        placeholder = { Text("Search menu items", color = Color(0xFF96C5A9)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun CategoryTabs(state: NewOrderUiState, onSelected: (Int) -> Unit) {
    // Categories rendered as chips in a FlowRow to wrap onto multiple lines as needed
    androidx.compose.foundation.layout.FlowRow(
        modifier = Modifier.fillMaxWidth().background(Color.Transparent).padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        state.categories.forEachIndexed { index, title ->
            val selected = index == state.selectedCategoryIndex
            com.zamulabs.dineeasepos.ui.components.ui.AppFilterChip(
                selected = selected,
                onClick = { onSelected(index) },
                label = title,
            )
        }
    }
}

@Composable
private fun OrderSummary(
    state: NewOrderUiState,
    onNotesChanged: (String) -> Unit,
    onPlaceOrder: () -> Unit,
    onEvent: (NewOrderUiEvent) -> Unit
) {
    Column(Modifier.width(360.dp)) {
        Text(
            "Table",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        // Table dropdown on side per design
        AppDropdown(
            label = "",
            selected = state.selectedTable,
            items = state.tables,
            onSelected = { onNotesChanged("") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Order Cart",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        state.cart.forEach { item ->
            Row(
                Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(item.title, color = Color.White); Text(
                    "Quantity: ${item.quantity}",
                    color = Color(0xFF96C5A9),
                    style = MaterialTheme.typography.bodySmall
                )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppButton(
                        onClick = { onEvent(NewOrderUiEvent.OnDecQty(item.title)) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF264532))
                    ) { Text("-") }
                    AppButton(
                        onClick = { onEvent(NewOrderUiEvent.OnIncQty(item.title)) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B))
                    ) { Text("+") }
                    AppButton(
                        onClick = { onEvent(NewOrderUiEvent.OnRemoveItem(item.title)) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1E1E))
                    ) { Text("Remove") }
                    Text("$" + String.format("%.2f", item.price), color = Color.White)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        AppTextField(
            value = state.notes,
            onValueChange = onNotesChanged,
            placeholder = { Text("Special Instructions", color = Color(0xFF96C5A9)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Order Summary",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Column(Modifier.padding(horizontal = 4.dp)) {
            SummaryRow("Subtotal", state.subtotal)
            SummaryRow("Tax", state.tax)
            SummaryRow("Total", state.total)
        }
        Spacer(Modifier.height(12.dp))
        AppButton(
            onClick = onPlaceOrder,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B))
        ) {
            Text("Place Order", color = Color(0xFF122118), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: Double) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color(0xFF96C5A9), style = MaterialTheme.typography.bodySmall)
        Text(
            "$" + String.format("%.2f", value),
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
