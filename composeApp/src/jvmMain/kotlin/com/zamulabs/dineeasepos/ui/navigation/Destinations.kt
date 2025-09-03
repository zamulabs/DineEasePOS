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
package com.zamulabs.dineeasepos.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object Login

    @Serializable
    object Order

    @Serializable
    object Menu

    // Payments list screen (analytics/history)
    @Serializable
    object Payment

    // Payment processing flow for a specific order
    @Serializable
    object PaymentProcessing

    @Serializable
    object Table

    @Serializable
    object AddTable

    @Serializable
    object AddMenuItem

    @Serializable
    object Dashboard

    @Serializable
    object NewOrder

    @Serializable
    object OrderDetails

    @Serializable
    object TableDetails

    @Serializable
    object Settings

    @Serializable
    object Users

    @Serializable
    object AddUser

    @Serializable
    object Reports

    @Serializable
    data class Receipt(val orderId: String)
}
