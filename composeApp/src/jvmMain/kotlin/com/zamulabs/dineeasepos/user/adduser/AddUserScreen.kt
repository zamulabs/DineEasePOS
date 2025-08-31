package com.zamulabs.dineeasepos.user.adduser

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
fun AddUserScreen(
    onBack: ()->Unit,
    onSaved: ()->Unit,
    modifier: Modifier = Modifier,
    vm: AddUserViewModel = koinInject<AddUserViewModel>()
){
    val state = vm.state

    AddUserScreenContent(
        state = state,
        onEvent = vm::onEvent,
        onSave = onSaved,
        onCancel = onBack,
        modifier = modifier,
    )
}
