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
package com.zamulabs.dineeasepos.ui.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.ui.components.table.AppDataTable
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppScreenTopBar

@Composable
fun SettingsScreenContent(
    state: SettingsUiState,
    onEvent: (SettingsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = { AppScreenTopBar(title = "Settings") },
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
            item { HorizontalDivider() }
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
