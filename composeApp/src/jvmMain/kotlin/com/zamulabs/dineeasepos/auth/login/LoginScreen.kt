package com.zamulabs.dineeasepos.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.navigation.Destinations

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    // Obtain VM via simple remember for now using our dedicated ViewModel class via default constructor
    val viewModel = remember { LoginViewModel() }
    val state = viewModel.uiState.collectAsState(initial = LoginUiState()).value

    LoginScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onLoginSuccess = { navController.navigate(Destinations.Dashboard) },
        modifier = modifier
    )
}