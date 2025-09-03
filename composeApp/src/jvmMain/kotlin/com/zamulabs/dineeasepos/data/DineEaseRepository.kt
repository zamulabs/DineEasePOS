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

import com.zamulabs.dineeasepos.ui.menu.MenuItem
import com.zamulabs.dineeasepos.ui.order.Order
import com.zamulabs.dineeasepos.ui.payment.PaymentItem
import com.zamulabs.dineeasepos.utils.NetworkResult

interface DineEaseRepository {
    suspend fun getMenu(): NetworkResult<List<MenuItem>>
    suspend fun addMenuItem(
        name: String,
        description: String?,
        price: Double,
        category: String,
        active: Boolean,
        prepTimeMinutes: Int?,
        ingredients: String?,
    ): NetworkResult<MenuItem>

    suspend fun getOrders(): NetworkResult<List<Order>>
    suspend fun getTables(): NetworkResult<List<com.zamulabs.dineeasepos.ui.table.DiningTable>>
    suspend fun getPayments(): NetworkResult<List<PaymentItem>>

    // Payment processing
    suspend fun processPayment(
        orderId: String,
        method: com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentMethod,
        amountReceived: Double?,
        gateway: String?,
    ): NetworkResult<String>

    // Users
    suspend fun getUsers(): NetworkResult<List<com.zamulabs.dineeasepos.ui.user.User>>
    suspend fun addUser(
        name: String,
        email: String,
        role: String,
        password: String,
        isActive: Boolean,
    ): NetworkResult<com.zamulabs.dineeasepos.ui.user.User>

    suspend fun addTable(
        number: String,
        name: String?,
        capacity: Int,
        location: String?
    ): NetworkResult<com.zamulabs.dineeasepos.ui.table.DiningTable>

    // Reports
    suspend fun getSalesReports(period: String?): NetworkResult<List<com.zamulabs.dineeasepos.ui.reports.SalesRow>>

    // Receipt
    suspend fun getReceipt(orderId: String): NetworkResult<com.zamulabs.dineeasepos.ui.receipt.ReceiptUiState>

    // Auth
    suspend fun login(email: String, password: String): NetworkResult<String>
    suspend fun forgotPassword(email: String): NetworkResult<String>
    suspend fun resetPassword(email: String, code: String, newPassword: String): NetworkResult<String>
}
