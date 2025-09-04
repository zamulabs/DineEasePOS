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
package com.zamulabs.dineeasepos.ui.reports

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.ui.components.table.AppDataTable
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppScreenTopBar
import com.zamulabs.dineeasepos.ui.components.ui.AppTextField

@Composable
fun ReportsScreenContent(
    state: ReportsUiState,
    onEvent: (ReportsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = { AppScreenTopBar(title = "Reports") },
        contentList = {
            item { Text("Analyze your business performance with detailed reports.", color = MaterialTheme.colorScheme.outline) }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)){
                    TabLabel("Sales", state.selectedTab==ReportsTab.Sales){ onEvent(ReportsUiEvent.OnTabSelected(ReportsTab.Sales)) }
                    TabLabel("Item Performance", state.selectedTab==ReportsTab.ItemPerformance){ onEvent(ReportsUiEvent.OnTabSelected(ReportsTab.ItemPerformance)) }
                    TabLabel("Payment Summary", state.selectedTab==ReportsTab.PaymentSummary){ onEvent(ReportsUiEvent.OnTabSelected(ReportsTab.PaymentSummary)) }
                }
            }
            item { HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant) }
            item { Text("Sales Overview", style = MaterialTheme.typography.titleLarge) }
            item {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    AppTextField(
                        value = state.period,
                        onValueChange = { onEvent(ReportsUiEvent.OnPeriodChanged(it)) },
                        modifier = Modifier.width(240.dp),
                        singleLine = true,
                        placeholder = { Text("") }
                    )
                }
            }
            item { Text("Total Sales", style = MaterialTheme.typography.titleMedium) }
            item { Text(state.totalSales, style = MaterialTheme.typography.headlineMedium) }
            item { Row{ Text("Last 30 Days ", color = MaterialTheme.colorScheme.outline); Text(state.delta, color = Color(0xFF0BDA43)) } }
            item { SalesChart(modifier = Modifier.fillMaxWidth().height(180.dp)) }
            item { Text("Sales Details", style = MaterialTheme.typography.titleLarge) }
            item {
                AppDataTable(
                    columns = listOf(
                        DataColumn { Text("Date") },
                        DataColumn { Text("Orders") },
                        DataColumn { Text("Gross Sales") },
                        DataColumn { Text("Discounts") },
                        DataColumn { Text("Net Sales") },
                    ),
                ){
                    state.rows.forEach { r ->
                        row {
                            cell { Text(r.date, color = MaterialTheme.colorScheme.outline) }
                            cell { Text(r.orders.toString(), color = MaterialTheme.colorScheme.outline) }
                            cell { Text(r.gross, color = MaterialTheme.colorScheme.outline) }
                            cell { Text(r.discounts, color = MaterialTheme.colorScheme.outline) }
                            cell { Text(r.net, color = MaterialTheme.colorScheme.outline) }
                        }
                    }
                }
            }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
                    AppButton(onClick = { onEvent(ReportsUiEvent.OnPrint) }){ Text("Print") }
                    Spacer(Modifier.width(8.dp))
                    AppButton(onClick = { onEvent(ReportsUiEvent.OnExport) }){ Text("Export Report") }
                }
            }

            // Item Performance: Combinations report
            if (state.selectedTab == ReportsTab.ItemPerformance) {
                item { Spacer(Modifier.height(16.dp)) }
                item { HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant) }
                item { Text("Item Combinations", style = MaterialTheme.typography.titleLarge) }
                item {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)){
                        AppTextField(
                            value = state.fromIso,
                            onValueChange = { v -> onEvent(ReportsUiEvent.OnCombinationsRangeChanged(v, state.toIso, state.limit)) },
                            modifier = Modifier.width(180.dp),
                            singleLine = true,
                            placeholder = { Text("From (YYYY-MM-DD)") }
                        )
                        AppTextField(
                            value = state.toIso,
                            onValueChange = { v -> onEvent(ReportsUiEvent.OnCombinationsRangeChanged(state.fromIso, v, state.limit)) },
                            modifier = Modifier.width(180.dp),
                            singleLine = true,
                            placeholder = { Text("To (YYYY-MM-DD)") }
                        )
                        AppTextField(
                            value = state.limit.toString(),
                            onValueChange = { v ->
                                val n = v.toIntOrNull() ?: state.limit
                                onEvent(ReportsUiEvent.OnCombinationsRangeChanged(state.fromIso, state.toIso, n))
                            },
                            modifier = Modifier.width(120.dp),
                            singleLine = true,
                            placeholder = { Text("Limit") }
                        )
                        AppButton(onClick = { onEvent(ReportsUiEvent.OnCombinationsRangeChanged(state.fromIso, state.toIso, state.limit)) }){ Text("Load") }
                    }
                }
                item {
                    AppDataTable(
                        columns = listOf(
                            DataColumn { Text("Item A") },
                            DataColumn { Text("Item B") },
                            DataColumn { Text("Count") },
                        ),
                    ){
                        state.combinations.forEach { row ->
                            row {
                                cell { Text(row.itemAName, color = MaterialTheme.colorScheme.outline) }
                                cell { Text(row.itemBName, color = MaterialTheme.colorScheme.outline) }
                                cell { Text(row.count.toString(), color = MaterialTheme.colorScheme.outline) }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun TabLabel(text: String, selected: Boolean, onClick: ()->Unit){
    val color = if (selected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.outline
    Column(Modifier.padding(vertical = 4.dp).clickable(onClick = onClick)){
        Text(text, color = color, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(6.dp))
        Box(Modifier.height(3.dp).width(40.dp).background(if (selected) Color(0xFF38E07B) else Color.Transparent))
    }
}

@Composable
private fun SalesChart(modifier: Modifier){
    val line = listOf(0.2f,0.6f,0.5f,0.45f,0.7f,0.4f,0.35f,0.8f,0.3f,0.2f,0.75f,0.5f,0.7f)
    Canvas(modifier){
        val w = size.width; val h = size.height
        val path = Path()
        line.forEachIndexed { i, v ->
            val x = w * i/(line.size-1)
            val y = h * (1f - v*0.9f)
            if(i==0) path.moveTo(x,y) else path.lineTo(x,y)
        }
        drawPath(path = path, color = Color(0xFF96C5A9), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f))
        val fill = Path().apply {
            addPath(path)
            lineTo(w,h); lineTo(0f,h); close()
        }
        drawPath(path = fill, brush = Brush.verticalGradient(listOf(Color(0xFF264532), Color(0x00264532))))
    }
}
