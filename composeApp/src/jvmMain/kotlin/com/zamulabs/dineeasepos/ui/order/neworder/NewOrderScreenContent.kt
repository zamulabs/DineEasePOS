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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

// Image dependency not available; using placeholder box

@Composable
fun NewOrderScreenContent(
    state: NewOrderUiState,
    onEvent: (NewOrderUiEvent) -> Unit,
    onPlaceOrder: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
){
    AppScaffold(
        modifier = modifier,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            BackBreadcrumb(
                parentLabel = "Orders",
                currentLabel = "New Order",
                onBack = onBack,
            )
        },
        content = { innerPadding ->
        Row(Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 24.dp, vertical = 16.dp)){
            Column(Modifier.weight(1f)){
                // Title provided by BackBreadcrumb top bar; avoid duplication
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth()){
                    TableDropdown(state){ onEvent(NewOrderUiEvent.OnTableSelected(it)) }
                }
                Spacer(Modifier.height(12.dp))
                SearchField(state.searchString){ onEvent(NewOrderUiEvent.OnSearch(it)) }
                Spacer(Modifier.height(12.dp))
                CategoryTabs(state){ onEvent(NewOrderUiEvent.OnCategorySelected(it)) }
                Spacer(Modifier.height(8.dp))
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 158.dp), contentPadding = PaddingValues(8.dp), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)){
                    items(state.items){ item ->
                        Column(Modifier.fillMaxWidth()){
                            Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(12.dp)).background(Color(0xFF223A2C)))
                            Spacer(Modifier.height(8.dp))
                            Text(item.title, color = Color.White, style = MaterialTheme.typography.titleMedium)
                            Text(item.description, color = Color(0xFF96C5A9), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            Spacer(Modifier.width(24.dp))
            OrderSummary(state = state, onNotesChanged = { onEvent(NewOrderUiEvent.OnNotesChanged(it)) }, onPlaceOrder = onPlaceOrder)
        }
    }
)
}

@Composable
private fun TableDropdown(state: NewOrderUiState, onChanged: (String)->Unit){
    var expanded by remember { mutableStateOf(false) }
    Box(Modifier.widthIn(max = 480.dp)){
        AppDropdown(
            label = "Table",
            selected = state.selectedTable,
            items = state.tables,
            onSelected = { onChanged(it) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SearchField(value: String, onChanged: (String)->Unit){
    AppTextField(
        value = value,
        onValueChange = onChanged,
        placeholder = { Text("Search menu items", color = Color(0xFF96C5A9)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun CategoryTabs(state: NewOrderUiState, onSelected: (Int)->Unit){
    Row(Modifier.fillMaxWidth().background(Color.Transparent), horizontalArrangement = Arrangement.spacedBy(24.dp)){
        state.categories.forEachIndexed { index, title ->
            val selected = index == state.selectedCategoryIndex
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)){
                Text(title, color = if(selected) Color.White else Color(0xFF96C5A9), style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                Spacer(Modifier.height(8.dp))
                Box(Modifier.height(3.dp).width(88.dp).background(if(selected) Color(0xFF38E07B) else Color.Transparent))
            }
        }
    }
}

@Composable
private fun OrderSummary(state: NewOrderUiState, onNotesChanged: (String)->Unit, onPlaceOrder: ()->Unit){
    Column(Modifier.width(360.dp)){
        Text("Order Summary", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
        Spacer(Modifier.height(12.dp))
        state.cart.forEach { item ->
            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Column{ Text(item.title, color = Color.White); Text("x${item.quantity}", color = Color(0xFF96C5A9), style = MaterialTheme.typography.bodySmall) }
                Text("$" + String.format("%.2f", item.price), color = Color.White)
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
        Column(Modifier.padding(horizontal = 4.dp)){
            SummaryRow("Subtotal", state.subtotal)
            SummaryRow("Tax", state.tax)
            SummaryRow("Total", state.total)
        }
        Spacer(Modifier.height(8.dp))
        AppButton(onClick = onPlaceOrder, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B))){
            Text("Place Order", color = Color(0xFF122118), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: Double){
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween){
        Text(label, color = Color(0xFF96C5A9), style = MaterialTheme.typography.bodySmall)
        Text("$" + String.format("%.2f", value), color = Color.White, style = MaterialTheme.typography.bodySmall)
    }
}

sealed interface NewOrderUiEvent{
    data class OnSearch(val query: String): NewOrderUiEvent
    data class OnTableSelected(val table: String): NewOrderUiEvent
    data class OnCategorySelected(val index: Int): NewOrderUiEvent
    data class OnNotesChanged(val notes: String): NewOrderUiEvent
}
