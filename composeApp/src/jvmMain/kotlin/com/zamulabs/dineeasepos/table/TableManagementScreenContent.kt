package com.zamulabs.dineeasepos.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
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

@Composable
fun TableManagementScreenContent(
    state: TableManagementUiState,
    onEvent: (TableManagementUiEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = { com.zamulabs.dineeasepos.components.ui.AppScreenTopBar(title = "Tables") },
        contentHorizontalPadding = 24.dp,
        contentList = {
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    // Title moved to TopAppBar; keep only actions here
                    com.zamulabs.dineeasepos.components.ui.AppButton(onClick = { onEvent(TableManagementUiEvent.OnClickAddTable) }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryLightColor)){
                        Text("Add Table")
                    }
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)){
                    AssistChip(onClick = {}, label = { Text("All Tables") }, colors = AssistChipDefaults.assistChipColors(containerColor = SecondaryLightColor))
                    AssistChip(onClick = {}, label = { Text("Available") }, colors = AssistChipDefaults.assistChipColors(containerColor = SecondaryLightColor))
                    AssistChip(onClick = {}, label = { Text("Occupied") }, colors = AssistChipDefaults.assistChipColors(containerColor = SecondaryLightColor))
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                AppDataTable(
                    columns = listOf(
                        DataColumn { Text("Table Number") },
                        DataColumn { Text("Status") },
                        DataColumn { Text("Capacity") },
                        DataColumn { Text("Actions") },
                    ),
                    paginated = false,
                ){
                    val items = state.tables
                    items.forEach { table ->
                        row {
                            cell { Text(table.number) }
                            cell {
                                val bg = Color(0xFF264532)
                                val textColor = if(table.status==TableStatus.Available) Color.White else Color.White
                                Row(modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(bg).padding(horizontal = 16.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically){
                                    Text(if(table.status==TableStatus.Available) "Available" else "Occupied", color = textColor)
                                }
                            }
                            cell { Text(table.capacity.toString(), color = Color(0xFFA6C7B5)) }
                            cell {
                                IconButton(onClick = { onEvent(TableManagementUiEvent.OnClickViewDetails(table.number)) }) {
                                    Icon(imageVector = Icons.Filled.Visibility, contentDescription = "View Details", tint = Color(0xFFA6C7B5))
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
