package com.zamulabs.dineeasepos.user.adduser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AddUserScreenContent(
    state: AddUserUiState,
    onEvent: (AddUserUiEvent)->Unit,
    onSave: ()->Unit,
    onCancel: ()->Unit,
    modifier: Modifier = Modifier,
){
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = {
            com.zamulabs.dineeasepos.components.ui.BackBreadcrumb(
                parentLabel = "Users",
                currentLabel = "Add User",
                onBack = onCancel,
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
                    com.zamulabs.dineeasepos.components.ui.AppOutlinedButton(onClick = { onCancel(); onEvent(AddUserUiEvent.OnCancel) }, colors = ButtonDefaults.outlinedButtonColors()) { Text("Cancel") }
                    Spacer(Modifier.width(12.dp))
                    com.zamulabs.dineeasepos.components.ui.AppButton(onClick = { onEvent(AddUserUiEvent.OnSave); onSave() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B))){ Text("Save", color = Color(0xFF122118)) }
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
private fun LabeledSelect(label: String, value: String, options: List<String>, onChanged: (String)->Unit){
    Column(Modifier.padding(vertical = 6.dp).widthIn(max = 480.dp)){
        Text(label, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium))
        Spacer(Modifier.height(8.dp))
        var expanded by remember { mutableStateOf(false) }
        Box{
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
