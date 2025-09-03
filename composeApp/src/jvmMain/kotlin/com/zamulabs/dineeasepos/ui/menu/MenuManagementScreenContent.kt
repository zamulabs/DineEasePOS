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
package com.zamulabs.dineeasepos.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
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
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.ui.components.table.AppDataTable
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppScreenTopBar
import com.zamulabs.dineeasepos.ui.components.ui.AppTextField
import com.zamulabs.dineeasepos.ui.theme.PrimaryLightColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuManagementScreenContent(
    state: MenuManagementUiState,
    onEvent: (MenuManagementUiEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    AppScaffold(
        modifier = modifier,
        topBar = { AppScreenTopBar(title = "Menu Management") },
        contentHorizontalPadding = 24.dp,
        contentList = {
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    // Title moved to TopAppBar; keep only actions here
                    AppButton(onClick = { onEvent(MenuManagementUiEvent.OnClickAddItem) }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryLightColor)){
                        Text("Add Item")
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                var query by remember(state.searchString){ mutableStateOf(state.searchString) }
                AppTextField(
                    value = query,
                    onValueChange = { query = it; onEvent(MenuManagementUiEvent.OnSearch(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search menu items") },
                    singleLine = true,
                )
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                val tabs = listOf(MenuTab.All, MenuTab.Active, MenuTab.Inactive)
                val selectedIndex = tabs.indexOf(state.selectedTab).coerceAtLeast(0)
                SecondaryScrollableTabRow(selectedTabIndex = selectedIndex, containerColor = Color.Transparent, divider = {}, edgePadding = 0.dp){
                    tabs.forEachIndexed { index, tab ->
                        Tab(selected = index==selectedIndex, onClick = { onEvent(MenuManagementUiEvent.OnTabSelected(tab)) }, text = { Text(tab.name.replaceFirstChar { it.uppercase() }) })
                    }
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                AppDataTable(
                    columns = listOf(
                        DataColumn { Text("Name") },
                        DataColumn { Text("Category") },
                        DataColumn { Text("Price") },
                        DataColumn { Text("Status") },
                        DataColumn { Text("Actions") },
                    ),
                    paginated = false,
                ){
                    val filtered = state.items.filter { item ->
                        val q = state.searchString.trim().lowercase()
                        q.isEmpty() || item.name.lowercase().contains(q) || item.category.lowercase().contains(q)
                    }.filter { item ->
                        when(state.selectedTab){
                            MenuTab.All -> true
                            MenuTab.Active -> item.active
                            MenuTab.Inactive -> !item.active
                        }
                    }
                    filtered.forEachIndexed { index, item ->
                        row {
                            cell { Text(item.name) }
                            cell { Text(item.category, color = Color(0xFFA6C7B5)) }
                            cell { Text(item.price, color = Color(0xFFA6C7B5)) }
                            cell {
                                val bg = Color(0xFF264532)
                                val active = item.active
                                val pillColor = if(active) PrimaryLightColor else Color(0xFF264532)
                                val textColor = if(active) Color.White else Color.White
                                Row(modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(bg).padding(horizontal = 16.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically){
                                    Text(if(active) "Active" else "Inactive")
                                }
                            }
                            cell {
                                TextButton(onClick = { onEvent(MenuManagementUiEvent.OnEdit(index)) }){ Text("Edit", color = Color(0xFFA6C7B5), fontWeight = FontWeight.Bold) }
                            }
                        }
                    }
                }
            }
        }
    )
}
