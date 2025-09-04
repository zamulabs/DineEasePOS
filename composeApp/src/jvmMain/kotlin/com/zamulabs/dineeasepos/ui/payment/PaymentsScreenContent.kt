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
package com.zamulabs.dineeasepos.ui.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.ui.components.table.AppDataTable
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppDropdown
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppScreenTopBar
import com.zamulabs.dineeasepos.ui.theme.SecondaryLightColor

@Composable
fun PaymentsScreenContent(
    state: PaymentsUiState,
    onEvent: (PaymentsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = { AppScreenTopBar(title = "Payments") },
        contentList = {
            item { Spacer(Modifier.height(16.dp)) }
            item {
                Column(Modifier.fillMaxWidth().padding(horizontal = 4.dp)){
                    // Title moved to TopAppBar
                    Text("View and manage all payments", color = MaterialTheme.colorScheme.outline)
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item { Text("Filters", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 4.dp)) }
            item {
                Row(Modifier.padding(horizontal = 4.dp, vertical = 8.dp)){
                    var expanded by remember { mutableStateOf(false) }
                    var selected by remember(state.filter) { mutableStateOf(state.filter) }
                    Box{
                        AppDropdown(
                            label = "Method",
                            selected = selected.ifBlank { null },
                            items = listOf("All","Cash","Credit Card","Mobile Payment"),
                            onSelected = {
                                selected = it
                                onEvent(PaymentsUiEvent.OnFilterChanged(it))
                            },
                        )
                    }
                }
            }
            item { CalendarPreviewSection() }
            item {
                Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp), horizontalArrangement = Arrangement.End){
                    AppButton(onClick = { onEvent(PaymentsUiEvent.OnExport) }) { Text("Export") }
                }
            }
            item {
                AppDataTable(
                    columns = listOf(
                        DataColumn { Text("Date") },
                        DataColumn { Text("Order ID") },
                        DataColumn { Text("Method") },
                        DataColumn { Text("Amount") },
                        DataColumn { Text("Status") },
                    ),
                ){
                    for (p in state.items){
                        row{
                            cell { Text(p.date, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)) }
                            cell { Text(p.orderId, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)) }
                            cell { Text(p.method, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)) }
                            cell { Text(p.amount, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)) }
                            cell {
                                Row(modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(Color(0xFF264532)).padding(horizontal = 16.dp, vertical = 6.dp)){
                                    Text(p.status, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun CalendarPreviewSection(){
    // Lightweight visual to match design impression (not a full calendar control)
    Row(Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)){
        FakeMonth("July 2024", highlightStart = true)
        FakeMonth("August 2024", highlightEnd = true)
    }
}

@Composable
private fun FakeMonth(title: String, highlightStart: Boolean = false, highlightEnd: Boolean = false){
    Column(Modifier.width(336.dp)){
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            if(highlightStart) IconButton(onClick = { }){ Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null) } else Spacer(Modifier.width(48.dp))
            Text(title, style = MaterialTheme.typography.titleMedium)
            if(highlightEnd) IconButton(onClick = { }){ Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null) } else Spacer(Modifier.width(48.dp))
        }
        Spacer(Modifier.height(8.dp))
        // Single row showing selected range 5-7
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
            for (d in listOf("5","6","7")){
                val bg = if(d=="6") Color(0xFF38E07B) else Color(0xFF264532)
                val text = if(d=="6") Color(0xFF122118) else Color.White
                Box(Modifier.size(36.dp).clip(RoundedCornerShape(18.dp)).background(bg), contentAlignment = Alignment.Center){
                    Text(d, color = text)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(color = SecondaryLightColor)
    }
}
