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
package com.zamulabs.dineeasepos.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
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
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppScreenTopBar
import com.zamulabs.dineeasepos.ui.components.ui.AppTextField
import com.zamulabs.dineeasepos.ui.theme.PrimaryLightColor
import com.zamulabs.dineeasepos.ui.theme.SecondaryLightColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderManagementScreenContent(
    state: OrderManagementUiState,
    onEvent: (OrderManagementUiEvent) -> Unit,
    onOrderClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = { AppScreenTopBar(title = "Orders") },
        contentHorizontalPadding = 24.dp,
        contentList = {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    // Title moved to TopAppBar; keep only actions here
                    AppButton(onClick = { onEvent(OrderManagementUiEvent.OnClickNewOrder) }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryLightColor)) {
                        Text("New Order")
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                var query by remember(state.searchString) { mutableStateOf(state.searchString) }
                AppTextField(
                    value = query,
                    onValueChange = {
                        query = it
                        onEvent(OrderManagementUiEvent.OnSearch(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search orders") },
                    singleLine = true,
                )
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AssistChip(onClick = { /* open status filter */ }, label = { Text("Status") }, trailingIcon = {}, colors = AssistChipDefaults.assistChipColors(containerColor = SecondaryLightColor))
                    AssistChip(onClick = { /* open table filter */ }, label = { Text("Table") }, colors = AssistChipDefaults.assistChipColors(containerColor = SecondaryLightColor))
                    AssistChip(onClick = { /* open date filter */ }, label = { Text("Date") }, colors = AssistChipDefaults.assistChipColors(containerColor = SecondaryLightColor))
                }
            }
            item { Spacer(Modifier.height(8.dp)) }
            item {
                val tabs = listOf(OrderTab.All, OrderTab.Open, OrderTab.Completed)
                val selectedIndex = tabs.indexOf(state.selectedTab).coerceAtLeast(0)
                SecondaryScrollableTabRow(
                    selectedTabIndex = selectedIndex,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    edgePadding = 0.dp,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = index == selectedIndex,
                            onClick = { onEvent(OrderManagementUiEvent.OnTabSelected(tab)) },
                            text = { Text(tab.name) },
                        )
                    }
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                AppDataTable(
                    columns = listOf(
                        DataColumn { Text("Order ID") },
                        DataColumn { Text("Table") },
                        DataColumn { Text("Status") },
                        DataColumn { Text("Total") },
                        DataColumn { Text("Time") },
                        DataColumn { Text("Actions") },
                    ),
                    paginated = true,
                ) {
                    val filtered = state.orders.filter { order ->
                        val q = state.searchString.trim().lowercase()
                        q.isEmpty() || order.id.lowercase().contains(q) || order.table.lowercase().contains(q)
                    }.filter { order ->
                        when(state.selectedTab){
                            OrderTab.All -> true
                            OrderTab.Open -> order.status == OrderStatus.Open
                            OrderTab.Completed -> order.status == OrderStatus.Completed
                        }
                    }
                    for(order in filtered){
                        row {
                            val oid = order.id
                            cell { Text(order.id, modifier = Modifier.clickable { onOrderClick(order.id) }) }
                            cell { Text(order.table) }
                            cell {
                                val bg = if (order.status == OrderStatus.Open) PrimaryLightColor.copy(alpha = 0.2f) else Color(0xFF2E4B3A)
                                val textColor = if (order.status == OrderStatus.Open) PrimaryLightColor else Color(0xFFA6C7B5)
                                Row(modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(bg).padding(horizontal = 16.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text(if(order.status==OrderStatus.Open) "Open" else "Completed", color = textColor)
                                }
                            }
                            cell { Text(order.total) }
                            cell { Text(order.time) }
                            cell {
                                androidx.compose.material3.IconButton(onClick = { onEvent(OrderManagementUiEvent.OnClickViewDetails(oid)) }) {
                                    androidx.compose.material3.Icon(
                                        imageVector = androidx.compose.material.icons.Icons.Filled.Visibility,
                                        contentDescription = "View Details",
                                        tint = Color(0xFFA6C7B5)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
