package com.zamulabs.dineeasepos.table.addtable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AddTableScreenContent(
    state: AddTableUiState,
    onEvent: (AddTableUiEvent) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = {
            com.zamulabs.dineeasepos.components.ui.BackBreadcrumb(
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
                    com.zamulabs.dineeasepos.components.ui.AppOutlinedButton(onClick = {
                        onCancel(); onEvent(
                        AddTableUiEvent.OnCancel
                    )
                    }) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(12.dp))
                    com.zamulabs.dineeasepos.components.ui.AppButton(onClick = {
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
        com.zamulabs.dineeasepos.components.ui.AppTextField(
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
        var expanded = remember { mutableStateOf(false) }
        Box {
            com.zamulabs.dineeasepos.components.ui.AppDropdown(
                label = label,
                selected = value,
                items = options,
                onSelected = { onChanged(it) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
