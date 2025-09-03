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

import com.zamulabs.dineeasepos.data.dto.MenuResponseDto.Companion.toDomainModel
import com.zamulabs.dineeasepos.ui.menu.MenuItem
import com.zamulabs.dineeasepos.ui.order.Order
import com.zamulabs.dineeasepos.ui.payment.PaymentItem
import com.zamulabs.dineeasepos.utils.NetworkResult
import com.zamulabs.dineeasepos.utils.safeApiCall
import io.github.aakira.napier.Napier

class DineEaseRepositoryImpl(
    private val apiService: ApiService,
) : DineEaseRepository {
    override suspend fun getMenu(): NetworkResult<List<MenuItem>> {
        return safeApiCall {
            Napier.e("Fetching menu")
            apiService.fetchMenu().map { it.toDomainModel() }
        }
    }

    override suspend fun addMenuItem(
        name: String,
        description: String?,
        price: Double,
        category: String,
        active: Boolean,
        prepTimeMinutes: Int?,
        ingredients: String?,
    ): NetworkResult<MenuItem> {
        return safeApiCall {
            Napier.e("Creating menu item")
            val response = apiService.createMenuItem(
                com.zamulabs.dineeasepos.data.dto.CreateMenuItemRequestDto(
                    name = name,
                    description = description,
                    price = price,
                    category = category,
                    active = active,
                    prepTimeMinutes = prepTimeMinutes,
                    ingredients = ingredients,
                )
            )
            com.zamulabs.dineeasepos.data.dto.MenuResponseDto.Companion.run { response.toDomainModel() }
        }
    }

    override suspend fun getOrders(): NetworkResult<List<Order>> {
        return safeApiCall {
            Napier.e("Fetching orders")
            apiService.fetchOrders().map { com.zamulabs.dineeasepos.data.dto.OrderMappers.run { it.toDomain() } }
        }
    }

    override suspend fun getTables(): NetworkResult<List<com.zamulabs.dineeasepos.ui.table.DiningTable>> {
        return safeApiCall {
            Napier.e("Fetching tables")
            apiService.fetchTables().map { com.zamulabs.dineeasepos.data.dto.TableMappers.run { it.toDomain() } }
        }
    }

    override suspend fun getPayments(): NetworkResult<List<PaymentItem>> {
        return safeApiCall {
            Napier.e("Fetching payments")
            apiService.fetchPayments().map { com.zamulabs.dineeasepos.data.dto.PaymentMappers.run { it.toDomain() } }
        }
    }

    override suspend fun getUsers(): NetworkResult<List<com.zamulabs.dineeasepos.ui.user.User>> {
        return safeApiCall {
            Napier.e("Fetching users")
            apiService.fetchUsers().map { com.zamulabs.dineeasepos.data.dto.UserMappers.run { it.toDomain() } }
        }
    }

    override suspend fun addUser(
        name: String,
        email: String,
        role: String,
        password: String,
        isActive: Boolean,
    ): NetworkResult<com.zamulabs.dineeasepos.ui.user.User> {
        return safeApiCall {
            Napier.e("Creating user")
            val response = apiService.createUser(
                com.zamulabs.dineeasepos.data.dto.CreateUserRequestDto(
                    name = name,
                    email = email,
                    role = role,
                    password = password,
                    isActive = isActive,
                )
            )
            com.zamulabs.dineeasepos.data.dto.UserMappers.run { response.toDomain() }
        }
    }

    override suspend fun addTable(
        number: String,
        name: String?,
        capacity: Int,
        location: String?
    ): NetworkResult<com.zamulabs.dineeasepos.ui.table.DiningTable> {
        return safeApiCall {
            Napier.e("Creating table")
            val response = apiService.createTable(
                com.zamulabs.dineeasepos.data.dto.CreateTableRequestDto(
                    number = number,
                    name = name,
                    capacity = capacity,
                    location = location
                )
            )
            com.zamulabs.dineeasepos.data.dto.TableMappers.run { response.toDomain() }
        }
    }

    override suspend fun getSalesReports(period: String?): NetworkResult<List<com.zamulabs.dineeasepos.ui.reports.SalesRow>> {
        return safeApiCall {
            Napier.e("Fetching sales reports")
            apiService.fetchSalesReports(period).map { com.zamulabs.dineeasepos.data.dto.ReportsMappers.run { it.toDomain() } }
        }
    }

    override suspend fun getReceipt(orderId: String): NetworkResult<com.zamulabs.dineeasepos.ui.receipt.ReceiptUiState> {
        return safeApiCall {
            Napier.e("Fetching receipt for order $orderId")
            val dto = apiService.fetchReceipt(orderId)
            com.zamulabs.dineeasepos.data.dto.ReceiptMappers.run { dto.toUiState() }
        }
    }

    override suspend fun processPayment(
        orderId: String,
        method: com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentMethod,
        amountReceived: Double?,
        gateway: String?,
    ): NetworkResult<String> {
        return safeApiCall {
            Napier.e("Processing payment for order $orderId")
            val request = com.zamulabs.dineeasepos.data.dto.CreatePaymentRequestDto(
                orderId = orderId,
                method = com.zamulabs.dineeasepos.data.dto.PaymentMappers.run { method.toDto() },
                amountReceived = amountReceived,
                gateway = gateway,
            )
            val response = apiService.createPayment(request)
            response.status
        }
    }

    // Auth
    override suspend fun login(email: String, password: String): NetworkResult<String> {
        return safeApiCall {
            Napier.e("Logging in user $email")
            val dto = apiService.login(
                com.zamulabs.dineeasepos.data.dto.LoginRequestDto(email = email, password = password)
            )
            dto.token
        }
    }

    override suspend fun forgotPassword(email: String): NetworkResult<String> {
        return safeApiCall {
            Napier.e("Requesting password reset for $email")
            val dto = apiService.forgotPassword(
                com.zamulabs.dineeasepos.data.dto.ForgotPasswordRequestDto(email = email)
            )
            dto.message
        }
    }

    override suspend fun resetPassword(email: String, code: String, newPassword: String): NetworkResult<String> {
        return safeApiCall {
            Napier.e("Resetting password for $email")
            val dto = apiService.resetPassword(
                com.zamulabs.dineeasepos.data.dto.ResetPasswordRequestDto(
                    email = email,
                    code = code,
                    newPassword = newPassword,
                )
            )
            dto.message
        }
    }
}
