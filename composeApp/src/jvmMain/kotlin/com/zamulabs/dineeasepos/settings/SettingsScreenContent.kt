package com.zamulabs.dineeasepos.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.components.table.AppDataTable

@Composable
fun SettingsScreenContent(
    state: SettingsUiState,
    onEvent: (SettingsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = { com.zamulabs.dineeasepos.components.ui.AppScreenTopBar(title = "Settings") },
        contentList = {
            item {
                // Tabs header
                Row(Modifier.fillMaxWidth()){
                    val tabs = listOf(
                        SettingsTab.General to "General",
                        SettingsTab.Payments to "Payments",
                        SettingsTab.Receipts to "Receipts",
                        SettingsTab.Taxes to "Taxes",
                        SettingsTab.System to "System",
                    )
                    tabs.forEach { (tab,label) ->
                        TextButton(onClick = { onEvent(SettingsUiEvent.OnTabSelected(tab)) }){
                            Text(label, color = if(state.activeTab==tab) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.outline)
                        }
                    }
                }
            }
            item { Divider() }
            if(state.activeTab==SettingsTab.Taxes){
                item { Text("Tax rates", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) }
                item {
                    AppDataTable(
                        columns = listOf(
                            DataColumn { Text("Name") },
                            DataColumn { Text("Rate") },
                            DataColumn { Text("Status") },
                        ),
                    ){
                        state.taxRates.forEach { rate ->
                            row {
                                cell { Text(rate.name) }
                                cell { Text(rate.rate, color = MaterialTheme.colorScheme.outline) }
                                cell {
                                    FilledTonalButton(onClick = {}, shape = MaterialTheme.shapes.extraLarge, modifier = Modifier.height(32.dp)){
                                        Text(if(rate.active) "Active" else "Inactive")
                                    }
                                }
                            }
                        }
                    }
                }
                item { Spacer(Modifier.height(16.dp)) }
                item { Button(onClick = { onEvent(SettingsUiEvent.OnAddTaxRate) }, shape = MaterialTheme.shapes.extraLarge){ Text("Add tax rate") } }
            }
        }
    )
}
