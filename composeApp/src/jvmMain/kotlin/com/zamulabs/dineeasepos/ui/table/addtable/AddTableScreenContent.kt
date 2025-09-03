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
package com.zamulabs.dineeasepos.ui.table.addtable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppDropdown
import com.zamulabs.dineeasepos.ui.components.ui.AppOutlinedButton
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppTextField
import com.zamulabs.dineeasepos.ui.components.ui.BackBreadcrumb

@Composable
fun AddTableScreenContent(
    state: AddTableUiState,
    onEvent: (AddTableUiEvent) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
                parentLabel = "Tables",
                currentLabel = "Add Table",
                onBack = onCancel,
            )
        },
        contentList = {
            // Title provided by BackBreadcrumb top bar; avoid duplication
            item { Spacer(Modifier.height(8.dp)) }
            item {
                Column(Modifier.widthIn(max = 512.dp)) {
                    LabeledTextField(
                        label = "Table Number",
                        placeholder = "Enter table number",
                        value = state.tableNumber,
                        onValueChange = { onEvent(AddTableUiEvent.OnTableNumberChanged(it)) }
                    )
                    LabeledTextField(
                        label = "Table Name",
                        placeholder = "Enter table name",
                        value = state.tableName,
                        onValueChange = { onEvent(AddTableUiEvent.OnTableNameChanged(it)) }
                    )
                    LabeledTextField(
                        label = "Capacity",
                        placeholder = "Enter table capacity",
                        value = state.capacity,
                        onValueChange = { onEvent(AddTableUiEvent.OnCapacityChanged(it)) }
                    )
                    LabeledSelect(
                        label = "Location",
                        value = state.location.ifEmpty { state.locations.first() },
                        options = state.locations,
                        onChanged = { onEvent(AddTableUiEvent.OnLocationChanged(it)) }
                    )
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    AppOutlinedButton(onClick = {
                        onCancel(); onEvent(
                        AddTableUiEvent.OnCancel
                    )
                    }) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(12.dp))
                    AppButton(onClick = {
                        onEvent(
                            AddTableUiEvent.OnSave
                        ); onSave()
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B))) {
                        Text("Save", color = Color(0xFF122118))
                    }
                }
            }
        }
    )
}

@Composable
private fun LabeledTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(Modifier.padding(vertical = 6.dp).widthIn(max = 480.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium))
        Spacer(Modifier.height(8.dp))
        AppTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xFF96C5A9)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun LabeledSelect(
    label: String,
    value: String,
    options: List<String>,
    onChanged: (String) -> Unit
) {
    Column(Modifier.padding(vertical = 6.dp).widthIn(max = 480.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium))
        Spacer(Modifier.height(8.dp))
        Box {
            AppDropdown(
                label = label,
                selected = value,
                items = options,
                onSelected = { onChanged(it) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
