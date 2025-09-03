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
package com.zamulabs.dineeasepos.ui.user.adduser

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
fun AddUserScreenContent(
    state: AddUserUiState,
    onEvent: (AddUserUiEvent)->Unit,
    modifier: Modifier = Modifier,
){
    AppScaffold(
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
                parentLabel = "Users",
                currentLabel = "Add User",
                onBack = {
                    onEvent(AddUserUiEvent.OnSave)
                },
            )
        },
        contentList = {
            // Title moved to BackBreadcrumb; keep description only if needed
            item {
                Text("Add a new user to your team", color = Color(0xFF96C5A9))
                Spacer(Modifier.height(8.dp))
            }
            item { LabeledTextField("Name","Enter name", state.name){ onEvent(AddUserUiEvent.OnNameChanged(it)) } }
            item { LabeledTextField("Email","Enter email", state.email){ onEvent(AddUserUiEvent.OnEmailChanged(it)) } }
            item { LabeledSelect("Role", state.role.ifEmpty { state.roles.first() }, state.roles){ onEvent(AddUserUiEvent.OnRoleChanged(it)) } }
            item {
                Text("Password", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 20.dp, bottom = 8.dp))
                LabeledTextField("Password","Enter password", state.password){ onEvent(AddUserUiEvent.OnPasswordChanged(it)) }
                LabeledTextField("Confirm Password","Confirm password", state.confirmPassword){ onEvent(AddUserUiEvent.OnConfirmPasswordChanged(it)) }
            }
            item {
                Text("Status", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 20.dp, bottom = 8.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically){
                    Text("Active", color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
                    Switch(checked = state.isActive, onCheckedChange = { onEvent(AddUserUiEvent.OnActiveChanged(it)) }, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF38E07B), uncheckedTrackColor = Color(0xFF264532)))
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                    AppOutlinedButton(onClick = { onEvent(AddUserUiEvent.OnCancel) }, colors = ButtonDefaults.outlinedButtonColors()) { Text("Cancel") }
                    Spacer(Modifier.width(12.dp))
                    AppButton(onClick = { onEvent(AddUserUiEvent.OnSave) }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B))){ Text("Save", color = Color(0xFF122118)) }
                }
            }
        }
    )
}

@Composable
private fun LabeledTextField(label: String, placeholder: String, value: String, onValueChange: (String)->Unit){
    Column(Modifier.padding(vertical = 6.dp).widthIn(max = 480.dp)){
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
private fun LabeledSelect(label: String? = null, value: String, options: List<String>, onChanged: (String)->Unit){
    Column(Modifier.padding(vertical = 6.dp).widthIn(max = 480.dp)){
        label?.let { Text(it, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)) }
        Spacer(Modifier.height(8.dp))
        Box{
            AppDropdown(
                label = label.orEmpty(),
                selected = value,
                items = options,
                onSelected = { onChanged(it) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
