package com.zamulabs.dineeasepos.menu.addmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
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
fun AddMenuItemScreenContent(
    state: AddMenuItemUiState,
    onEvent: (AddMenuItemUiEvent) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = {
            com.zamulabs.dineeasepos.components.ui.BackBreadcrumb(
                parentLabel = "Menu",
                currentLabel = "Add Menu Item",
                onBack = onCancel,
            )
        },
        contentList = {
            // Title moved to TopBar BackBreadcrumb; avoid duplicate header
            item { Spacer(Modifier.height(4.dp)) }
            item {
                LabeledTextField(
                    label = "Item Name",
                    placeholder = "e.g., Spicy Chicken Sandwich",
                    value = state.name
                ) { onEvent(AddMenuItemUiEvent.OnNameChanged(it)) }

            }
            item {
                LabeledTextArea(
                    label = "Description",
                    placeholder = "Describe the menu item",
                    value = state.description
                ) { onEvent(AddMenuItemUiEvent.OnDescriptionChanged(it)) }
            }
            item {
                LabeledTextField(
                    label = "Price",
                    placeholder = "e.g., 9.99",
                    value = state.price
                ) { onEvent(AddMenuItemUiEvent.OnPriceChanged(it)) }
            }
            item {
                LabeledSelect(
                    label = "Category",
                    value = state.category.ifEmpty { state.categories.first() },
                    options = state.categories
                ) { onEvent(AddMenuItemUiEvent.OnCategoryChanged(it)) }
            }
            item {
                LabeledSelect(
                    label = "Status",
                    value = state.status.ifEmpty { state.statuses.first() },
                    options = state.statuses
                ) { onEvent(AddMenuItemUiEvent.OnStatusChanged(it)) }
            }
            item {
                LabeledTextField(
                    label = "Preparation Time (minutes)",
                    placeholder = "e.g., 15",
                    value = state.prepTimeMinutes
                ) { onEvent(AddMenuItemUiEvent.OnPrepTimeChanged(it)) }
            }
            item {
                LabeledTextArea(
                    label = "Ingredients",
                    placeholder = "List ingredients",
                    value = state.ingredients
                ) { onEvent(AddMenuItemUiEvent.OnIngredientsChanged(it)) }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                // Upload Image placeholder
                Box(
                    Modifier
                        .widthIn(max = 960.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1B3124))
                        .border(
                            width = 2.dp,
                            color = Color(0xFF366348),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Upload Image",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            "Click or drag an image here to upload",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = { /* TODO: file picker */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF264532))
                        ) { Text("Upload Image") }
                    }
                }
            }
            item {
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    com.zamulabs.dineeasepos.components.ui.AppOutlinedButton(onClick = {
                        onCancel(); onEvent(
                        AddMenuItemUiEvent.OnCancel
                    )
                    }) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(12.dp))
                    com.zamulabs.dineeasepos.components.ui.AppButton(onClick = {
                        onEvent(
                            AddMenuItemUiEvent.OnSave
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
private fun LabeledTextArea(
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
            singleLine = false,
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
