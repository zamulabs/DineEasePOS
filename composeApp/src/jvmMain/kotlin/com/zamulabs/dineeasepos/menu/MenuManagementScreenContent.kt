package com.zamulabs.dineeasepos.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.zamulabs.dineeasepos.theme.PrimaryLightColor
import com.zamulabs.dineeasepos.theme.SecondaryLightColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuManagementScreenContent(
    state: MenuManagementUiState,
    onEvent: (MenuManagementUiEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = { com.zamulabs.dineeasepos.components.ui.AppScreenTopBar(title = "Menu Management") },
        contentHorizontalPadding = 24.dp,
        contentList = { 
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    // Title moved to TopAppBar; keep only actions here
                    com.zamulabs.dineeasepos.components.ui.AppButton(onClick = { onEvent(MenuManagementUiEvent.OnClickAddItem) }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryLightColor)){
                        Text("Add Item")
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                var query by remember(state.searchString){ mutableStateOf(state.searchString) }
                com.zamulabs.dineeasepos.components.ui.AppTextField(
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
