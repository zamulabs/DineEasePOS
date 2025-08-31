package com.zamulabs.dineeasepos.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreenContent(
    state: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.Start
    ){
        Spacer(Modifier.height(24.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text("Welcome back", style = MaterialTheme.typography.headlineMedium)
        }
        Spacer(Modifier.height(12.dp))
        Column(Modifier.widthIn(max = 512.dp)){
            // Email
            Text("Email", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
            com.zamulabs.dineeasepos.components.ui.AppTextField(
                value = state.email,
                onValueChange = { onEvent(LoginUiEvent.OnEmailChanged(it)) },
                placeholder = { Text("Enter your email", color = Color(0xFF96C5A9)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Spacer(Modifier.height(16.dp))
            Text("Password", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
            com.zamulabs.dineeasepos.components.ui.AppTextField(
                value = state.password,
                onValueChange = { onEvent(LoginUiEvent.OnPasswordChanged(it)) },
                placeholder = { Text("Enter your password", color = Color(0xFF96C5A9)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            TextButton(onClick = { onEvent(LoginUiEvent.OnForgotPassword) }){ Text("Forgot Password?", color = MaterialTheme.colorScheme.outline, textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline) }
            com.zamulabs.dineeasepos.components.ui.AppButton(
                onClick = {
                    onEvent(LoginUiEvent.OnSubmit)
                    if(state.email.isNotBlank() && state.password.isNotBlank()) onLoginSuccess()
                },
                modifier = Modifier.fillMaxWidth(),
                height = 40.dp,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B), contentColor = Color(0xFF122118))
            ){
                Text("Login")
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                TextButton(onClick = { onEvent(LoginUiEvent.OnSignup) }){ Text("Don't have an account? Sign up", color = MaterialTheme.colorScheme.outline, textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline) }
            }
            if(state.error!=null){
                Text(state.error, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
