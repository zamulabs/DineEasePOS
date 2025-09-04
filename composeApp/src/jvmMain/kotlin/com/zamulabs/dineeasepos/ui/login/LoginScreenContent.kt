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
package com.zamulabs.dineeasepos.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppTextField

@Composable
fun LoginScreenContent(
    state: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = {},
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
//                .widthIn(max = 512.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Surface(
                    modifier = modifier.widthIn(max = 512.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp,
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Welcome back",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                        )

                        Spacer(Modifier.height(24.dp))

                        AppTextField(
                            value = state.email,
                            onValueChange = { onEvent(LoginUiEvent.OnEmailChanged(it)) },
                            placeholder = { Text("Enter your email", color = Color(0xFF96C5A9)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(Modifier.height(16.dp))

                        AppTextField(
                            value = state.password,
                            onValueChange = { onEvent(LoginUiEvent.OnPasswordChanged(it)) },
                            placeholder = {
                                Text(
                                    "Enter your password",
                                    color = Color(0xFF96C5A9)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(Modifier.height(16.dp))


                        TextButton(
                            onClick = { onEvent(LoginUiEvent.OnForgotPassword) }
                        ) {
                            Text(
                                "Forgot Password?",
                                color = MaterialTheme.colorScheme.outline,
                                textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                            )
                        }

                        AppButton(
                            onClick = { onEvent(LoginUiEvent.OnSubmit) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF38E07B),
                                contentColor = Color(0xFF122118)
                            )
                        ) {
                            Text("Login")
                        }

                        Spacer(Modifier.height(8.dp))
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onEvent(LoginUiEvent.OnSignup) }
                        ) {
                            Text(
                                "Don't have an account? Sign up",
                                color = MaterialTheme.colorScheme.outline,
                                textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline,
                                textAlign = TextAlign.Center,
                            )
                        }

                        if (state.error != null) {
                            Spacer(Modifier.height(8.dp))
                            Text(state.error, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}
