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

    override suspend fun updateMenuItem(
        name: String,
        description: String?,
        price: Double,
        category: String,
        active: Boolean,
        prepTimeMinutes: Int?,
        ingredients: String?,
    ): NetworkResult<MenuItem> {
        return safeApiCall {
            Napier.e("Updating menu item")
            val response = apiService.updateMenuItem(
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

    override suspend fun deleteMenuItem(name: String): NetworkResult<String> {
        return safeApiCall {
            Napier.e("Deleting menu item $name")
            apiService.deleteMenuItem(name)
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
            // Provide both a summary list item and full detail for UI
            com.zamulabs.dineeasepos.ui.receipt.ReceiptUiState(
                items = listOf(
                    com.zamulabs.dineeasepos.ui.receipt.ReceiptListItem(
                        receiptNo = "R-$orderId",
                        orderId = dto.orderId,
                        date = "${'$'}{dto.orderDate} ${'$'}{dto.orderTime}",
                        method = dto.paymentMethod,
                        amount = dto.total,
                    )
                ),
                detail = com.zamulabs.dineeasepos.ui.receipt.ReceiptDetail(
                    orderId = dto.orderId,
                    restaurantName = dto.restaurantName,
                    address = dto.address,
                    phone = dto.phone,
                    orderDate = dto.orderDate,
                    orderTime = dto.orderTime,
                    orderType = dto.orderType,
                    items = dto.items.map { com.zamulabs.dineeasepos.ui.receipt.ReceiptDetailItem(it.item, it.quantity, it.price, it.total) },
                    subtotal = dto.subtotal,
                    tax = dto.tax,
                    total = dto.total,
                    paymentMethod = dto.paymentMethod,
                )
            )
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

    // Extended APIs (mocked for end-to-end wiring without SQLDelight yet)
    override suspend fun placeCashSaleAndPay(
        lines: List<Pair<String, Double>>,
        tendered: Double,
    ): NetworkResult<String> = safeApiCall {
        Napier.e("Placing cash sale with ${'$'}{lines.size} lines; tendered=${'$'}tendered")
        // Simulate success status
        "Completed"
    }

    override suspend fun generateCombinationsReport(
        fromIso: String,
        toIso: String,
        limit: Int,
    ): NetworkResult<List<com.zamulabs.dineeasepos.ui.reports.CombinationRow>> = safeApiCall {
        Napier.e("Generating combinations report ${'$'}fromIso..${'$'}toIso limit=${'$'}limit")
        // Simple mocked pairs
        listOf(
            com.zamulabs.dineeasepos.ui.reports.CombinationRow("Burger", "Fries", 12),
            com.zamulabs.dineeasepos.ui.reports.CombinationRow("Tea", "Mandazi", 9),
        )
    }

    override suspend fun createUserAdmin(name: String, email: String, role: String): NetworkResult<String> = safeApiCall {
        Napier.e("Admin create user ${'$'}email role=${'$'}role")
        "Temp password: Abc123#xyz" // In real impl, return once and store hash only
    }

    override suspend fun resetPasswordAdmin(userId: String): NetworkResult<String> = safeApiCall {
        Napier.e("Admin reset password for user ${'$'}userId")
        "Temp password: XyZ!7890"
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): NetworkResult<String> = safeApiCall {
        Napier.e("Change password requested")
        "Password changed"
    }

    override suspend fun recordMorningPrep(items: List<Pair<String, Double>>): NetworkResult<String> = safeApiCall {
        Napier.e("Recording morning prep for ${'$'}{items.size} items")
        apiService.recordMorningPrep(items)
    }

    override suspend fun getStockMovements(): NetworkResult<List<com.zamulabs.dineeasepos.ui.dashboard.StockSummaryRow>> = safeApiCall {
        Napier.e("Fetching stock movements summary")
        apiService.fetchStockMovements().map { row ->
            com.zamulabs.dineeasepos.ui.dashboard.StockSummaryRow(
                item = row.item,
                prepared = row.prepared,
                sold = row.sold,
                remaining = row.remaining,
            )
        }
    }

    override suspend fun checkStock(itemName: String, requestedQty: Int): NetworkResult<Boolean> = safeApiCall {
        Napier.e("Checking stock $itemName -> $requestedQty")
        apiService.checkStock(itemName, requestedQty)
    }

    override suspend fun adjustStock(itemName: String, delta: Int): NetworkResult<Int> = safeApiCall {
        Napier.e("Adjust stock $itemName delta=${'$'}delta")
        apiService.adjustStock(itemName, delta)
    }
}
