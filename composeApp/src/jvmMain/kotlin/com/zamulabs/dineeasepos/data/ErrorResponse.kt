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
package com.zamulabs.dineeasepos.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("errors")
    val errors: List<String> = emptyList(),
    @SerialName("error")
    val error: List<String> = emptyList(),
    @SerialName("message")
    val message: String? = null,
    @SerialName("status_code")
    val statusCode: Int? = null,
    @SerialName("status_message")
    val statusMessage: String? = null,
)
