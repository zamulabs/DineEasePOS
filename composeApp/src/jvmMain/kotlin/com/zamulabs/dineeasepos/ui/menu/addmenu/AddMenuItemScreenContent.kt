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
package com.zamulabs.dineeasepos.ui.menu.addmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun AddMenuItemScreenContent(
    state: AddMenuItemUiState,
    onEvent: (AddMenuItemUiEvent) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
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
                    AppOutlinedButton(onClick = {
                        onCancel()
                    }) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(12.dp))
                    AppButton(onClick = {
                        onSave()
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
private fun LabeledTextArea(
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
