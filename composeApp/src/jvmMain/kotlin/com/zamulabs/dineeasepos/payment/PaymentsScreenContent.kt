package com.zamulabs.dineeasepos.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.components.table.AppDataTable
import com.zamulabs.dineeasepos.theme.SecondaryLightColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun PaymentsScreenContent(
    state: PaymentsUiState,
    onEvent: (PaymentsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = { com.zamulabs.dineeasepos.components.ui.AppScreenTopBar(title = "Payments") },
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
                        com.zamulabs.dineeasepos.components.ui.AppDropdown(
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
                    com.zamulabs.dineeasepos.components.ui.AppButton(onClick = { onEvent(PaymentsUiEvent.OnExport) }) { Text("Export") }
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
            if(highlightStart) IconButton(onClick = { }){ Icon(Icons.Default.ArrowBack, contentDescription = null) } else Spacer(Modifier.width(48.dp))
            Text(title, style = MaterialTheme.typography.titleMedium)
            if(highlightEnd) IconButton(onClick = { }){ Icon(Icons.Default.ArrowBack, contentDescription = null) } else Spacer(Modifier.width(48.dp))
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
        Divider(color = SecondaryLightColor)
    }
}
