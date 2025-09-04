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

import com.zamulabs.dineeasepos.data.dto.CreateMenuItemRequestDto
import com.zamulabs.dineeasepos.data.dto.CreatePaymentRequestDto
import com.zamulabs.dineeasepos.data.dto.CreateTableRequestDto
import com.zamulabs.dineeasepos.data.dto.CreateUserRequestDto
import com.zamulabs.dineeasepos.data.dto.ForgotPasswordRequestDto
import com.zamulabs.dineeasepos.data.dto.LoginRequestDto
import com.zamulabs.dineeasepos.data.dto.LoginResponseDto
import com.zamulabs.dineeasepos.data.dto.MenuResponseDto
import com.zamulabs.dineeasepos.data.dto.MessageResponseDto
import com.zamulabs.dineeasepos.data.dto.OrderResponseDto
import com.zamulabs.dineeasepos.data.dto.PaymentListItemDto
import com.zamulabs.dineeasepos.data.dto.PaymentResponseDto
import com.zamulabs.dineeasepos.data.dto.ReceiptDto
import com.zamulabs.dineeasepos.data.dto.ReceiptItemDto
import com.zamulabs.dineeasepos.data.dto.ResetPasswordRequestDto
import com.zamulabs.dineeasepos.data.dto.SalesReportDto
import com.zamulabs.dineeasepos.data.dto.TableResponseDto
import com.zamulabs.dineeasepos.data.dto.UserResponseDto
import io.ktor.client.HttpClient
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

/**
 * Fake implementation of ApiService that returns mocked DTOs and keeps in-memory state for mutations.
 * This preserves the flow DTOs -> Repository -> ViewModel -> Screen as required.
 */
class FakeApiService(
    httpClient: HttpClient,
) : ApiService(httpClient) {

    // In-memory storage
    private val menuItems = mutableListOf(
        MenuResponseDto(name = "Margherita Pizza", category = "Pizza", price = "8.99", active = true),
        MenuResponseDto(name = "Pepperoni Pizza", category = "Pizza", price = "10.99", active = true),
        MenuResponseDto(name = "Caesar Salad", category = "Salad", price = "6.50", active = true),
        MenuResponseDto(name = "Mushroom Soup", category = "Soup", price = "5.00", active = false),
    )

    private val tables = mutableListOf(
        TableResponseDto(id = "t1", number = "1", capacity = 4, status = "available"),
        TableResponseDto(id = "t2", number = "2", capacity = 2, status = "occupied"),
        TableResponseDto(id = "t3", number = "3", capacity = 6, status = "available"),
    )

    private val users = mutableListOf(
        UserResponseDto(id = "u1", name = "Alice", role = "Manager", active = true),
        UserResponseDto(id = "u2", name = "Bob", role = "Waiter", active = true),
        UserResponseDto(id = "u3", name = "Charlie", role = "Chef", active = false),
    )

    private val payments = mutableListOf(
        PaymentListItemDto(date = "2025-09-01", orderId = "O-1001", method = "Cash", amount = 18.50),
        PaymentListItemDto(date = "2025-09-02", orderId = "O-1002", method = "Online", amount = 32.00),
        PaymentListItemDto(date = "2025-09-03", orderId = "O-1003", method = "Cash", amount = 25.75),
    )

    private val orders = mutableListOf(
        OrderResponseDto(id = "O-1001", table = "2", status = "open", total = 18.50, time = "09:10 AM"),
        OrderResponseDto(id = "O-1002", table = "1", status = "completed", total = 32.00, time = "10:05 AM"),
        OrderResponseDto(id = "O-1003", table = "3", status = "open", total = 25.75, time = "10:30 AM"),
    )

    // Simple in-memory stock: item name -> available qty
    private val stock = mutableMapOf(
        "Margherita Pizza" to 10,
        "Pepperoni Pizza" to 8,
        "Caesar Salad" to 20,
        "Mushroom Soup" to 15,
    )
    // Morning prep baseline by item for current day (string quantities for simplicity)
    private var morningPrep: MutableMap<String, Double> = mutableMapOf(
        "Margherita Pizza" to 10.0,
        "Pepperoni Pizza" to 8.0,
        "Caesar Salad" to 20.0,
        "Mushroom Soup" to 15.0,
    )

    private fun nowDateTime(): Pair<String, String> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val date = "%04d-%02d-%02d".format(now.year, now.monthNumber, now.dayOfMonth)
        val time = "%02d:%02d".format(now.hour, now.minute)
        return date to time
    }

    override suspend fun fetchMenu(): List<MenuResponseDto> = menuItems.toList()

    override suspend fun createMenuItem(request: CreateMenuItemRequestDto): MenuResponseDto {
        val dto = MenuResponseDto(
            name = request.name,
            category = request.category,
            price = "%.2f".format(request.price),
            active = request.active,
        )
        menuItems.add(0, dto)
        return dto
    }

    override suspend fun updateMenuItem(request: CreateMenuItemRequestDto): MenuResponseDto {
        val idx = menuItems.indexOfFirst { it.name.equals(request.name, ignoreCase = true) }
        val dto = MenuResponseDto(
            name = request.name,
            category = request.category,
            price = "%.2f".format(request.price),
            active = request.active,
        )
        if (idx >= 0) {
            menuItems[idx] = dto
        } else {
            menuItems.add(0, dto)
        }
        return dto
    }

    override suspend fun deleteMenuItem(name: String): String {
        val removed = menuItems.removeIf { it.name.equals(name, ignoreCase = true) }
        return if (removed) "Deleted" else "Not found"
    }

    override suspend fun fetchTables(): List<TableResponseDto> = tables.toList()

    override suspend fun createTable(request: CreateTableRequestDto): TableResponseDto {
        val id = "t" + Random.nextInt(1000, 9999)
        val dto = TableResponseDto(id = id, number = request.number, capacity = request.capacity, status = "available")
        tables.add(dto)
        return dto
    }

    override suspend fun fetchUsers(): List<UserResponseDto> = users.toList()

    override suspend fun createUser(request: CreateUserRequestDto): UserResponseDto {
        val id = "u" + Random.nextInt(1000, 9999)
        val dto = UserResponseDto(id = id, name = request.name, role = request.role, active = request.isActive)
        users.add(dto)
        return dto
    }

    override suspend fun fetchSalesReports(period: String?): List<SalesReportDto> {
        // Simple sample across 3 days
        return listOf(
            SalesReportDto(date = "2025-09-01", orders = 15, gross = "$450.00", discounts = "$25.00", net = "$425.00"),
            SalesReportDto(date = "2025-09-02", orders = 20, gross = "$610.00", discounts = "$30.00", net = "$580.00"),
            SalesReportDto(date = "2025-09-03", orders = 12, gross = "$320.00", discounts = "$12.00", net = "$308.00"),
        )
    }

    override suspend fun fetchReceipt(orderId: String): ReceiptDto {
        val (date, time) = nowDateTime()
        val items = listOf(
            ReceiptItemDto(item = "Margherita Pizza", quantity = 1, price = "$8.99", total = "$8.99"),
            ReceiptItemDto(item = "Caesar Salad", quantity = 1, price = "$6.50", total = "$6.50"),
        )
        return ReceiptDto(
            orderId = orderId,
            restaurantName = "DineEase Restaurant",
            address = "123 Main St",
            phone = "+1 234 567 8900",
            orderDate = date,
            orderTime = time,
            orderType = "Dine-In",
            items = items,
            subtotal = "$15.49",
            tax = "$1.24",
            total = "$16.73",
            paymentMethod = "Cash",
        )
    }

    override suspend fun createPayment(request: CreatePaymentRequestDto): PaymentResponseDto {
        val (date, time) = nowDateTime()
        val resp = PaymentResponseDto(
            id = "p" + Random.nextInt(1000, 9999),
            orderId = request.orderId,
            status = "success",
            method = request.method,
            totalPaid = request.amountReceived ?: 0.0,
            createdAt = "$date $time",
        )
        payments.add(0, PaymentListItemDto(date = date, orderId = request.orderId, method = request.method, amount = resp.totalPaid))
        return resp
    }

    override suspend fun fetchPayments(): List<PaymentListItemDto> = payments.toList()

    override suspend fun fetchOrders(): List<OrderResponseDto> = orders.toList()

    override suspend fun login(request: LoginRequestDto): LoginResponseDto {
        // Always succeed with a dummy token for demo purposes
        return LoginResponseDto(token = "fake-token-${'$'}{request.email}")
    }

    override suspend fun forgotPassword(request: ForgotPasswordRequestDto): MessageResponseDto {
        return MessageResponseDto(message = "Reset code sent to ${'$'}{request.email}")
    }

    override suspend fun resetPassword(request: ResetPasswordRequestDto): MessageResponseDto {
        return MessageResponseDto(message = "Password reset successful for ${'$'}{request.email}")
    }

    // Stock mocked endpoints backed by 'stock' map
    override suspend fun checkStock(itemName: String, requestedQty: Int): Boolean {
        val available = stock[itemName] ?: 0
        return requestedQty <= available
    }

    override suspend fun adjustStock(itemName: String, delta: Int): Int {
        val current = stock[itemName] ?: 0
        val next = (current + delta).coerceAtLeast(0)
        stock[itemName] = next
        return next
    }

    override suspend fun recordMorningPrep(items: List<Pair<String, Double>>): String {
        // Set baseline for the day; also set current stock quantities to these values
        morningPrep.clear()
        items.forEach { (name, qty) -> morningPrep[name] = qty }
        // Update stock integers from double quantities
        stock.clear()
        items.forEach { (name, qty) -> stock[name] = kotlin.math.max(0, qty.toInt()) }
        return "Prep recorded"
    }

    override suspend fun fetchStockMovements(): List<com.zamulabs.dineeasepos.data.dto.StockMovementDto> {
        // Merge keys from both maps
        val keys = (morningPrep.keys + stock.keys).toSet()
        return keys.map { name ->
            val prepared = morningPrep[name] ?: 0.0
            val remaining = stock[name]?.toDouble() ?: 0.0
            val sold = (prepared - remaining).coerceAtLeast(0.0)
            com.zamulabs.dineeasepos.data.dto.StockMovementDto(
                item = name,
                prepared = if (prepared % 1.0 == 0.0) prepared.toInt().toString() else String.format("%.1f", prepared),
                sold = if (sold % 1.0 == 0.0) sold.toInt().toString() else String.format("%.1f", sold),
                remaining = if (remaining % 1.0 == 0.0) remaining.toInt().toString() else String.format("%.1f", remaining),
            )
        }
    }
}
