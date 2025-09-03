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
package com.zamulabs.dineeasepos.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.ui.components.table.AppDataTable
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppScreenTopBar

@Composable
fun DashboardScreenContent(
    state: DashboardUiState,
    onOrderClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = { AppScreenTopBar(title = "Dashboard") },
        contentHorizontalPadding = 16.dp,
        contentList = {
            // Title moved to TopAppBar via AppScreenTopBar to avoid duplication
            item { Spacer(Modifier.height(12.dp)) }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        title = "Today's Total Sales",
                        value = state.totalSalesToday,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "Orders Processed",
                        value = state.ordersProcessed,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "Pending Orders",
                        value = state.pendingOrders,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "Cash vs Online Payment",
                        value = state.cashVsOnline,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                Text(
                    text = "Top Selling Items",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            item { Spacer(Modifier.height(8.dp)) }
            item {
                AppDataTable(
                    columns = listOf(
                        DataColumn { Text("Item") },
                        DataColumn { Text("Quantity Sold") },
                        DataColumn { Text("Revenue") },
                    ),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    state.topSelling.forEach { item ->
                        row {
                            cell { Text(item.item) }
                            cell { Text(item.qty, color = MaterialTheme.colorScheme.primary) }
                            cell { Text(item.revenue, color = MaterialTheme.colorScheme.primary) }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                Text(
                    text = "Real-Time Order Tracking",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            item { Spacer(Modifier.height(8.dp)) }
            item {
                AppDataTable(
                    columns = listOf(
                        DataColumn { Text("Order ID") },
                        DataColumn { Text("Status") },
                        DataColumn { Text("Customer") },
                        DataColumn { Text("Order Time") },
                        DataColumn { Text("Total") },
                        DataColumn { Text("Actions") },
                    ),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    state.recentOrders.forEach { order ->
                        row {
                            cell {
                                Text(
                                    order.id,
                                    modifier = Modifier.clickable { onOrderClick(order.id) })
                            }
                            cell { StatusPill(order.status) }
                            cell { Text(order.customer, color = MaterialTheme.colorScheme.primary) }
                            cell { Text(order.time, color = MaterialTheme.colorScheme.primary) }
                            cell { Text(order.total, color = MaterialTheme.colorScheme.primary) }
                            cell {
                                IconButton(onClick = { onOrderClick(order.id) }) {
                                    Icon(
                                        imageVector = Icons.Filled.Visibility,
                                        contentDescription = "View Details",
                                        tint = MaterialTheme.colorScheme.primary
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

@Composable
private fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.heightIn(min = 84.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(6.dp))
            Text(
                value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun StatusPill(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        shape = MaterialTheme.shapes.large,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}
