package com.zamulabs.dineeasepos.payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun PaymentsScreen(){
    val vm = PaymentsViewModel()
    PaymentsScreenContent(state = vm.uiState, onEvent = vm::onEvent)
}
