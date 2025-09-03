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

import com.zamulabs.dineeasepos.data.dto.MenuResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

open class ApiService(
    protected val httpClient: HttpClient,
) {
    open suspend fun fetchMenu(): List<MenuResponseDto> {
        return httpClient.get(urlString = "api/menu") {
            parameter("all", 1)
        }.body()
    }

    open suspend fun createMenuItem(request: com.zamulabs.dineeasepos.data.dto.CreateMenuItemRequestDto): com.zamulabs.dineeasepos.data.dto.MenuResponseDto {
        return httpClient.post(urlString = "api/menu") {
            setBody(request)
        }.body()
    }

    open suspend fun fetchTables(): List<com.zamulabs.dineeasepos.data.dto.TableResponseDto> {
        return httpClient.get(urlString = "api/tables") {
            parameter("all", 1)
        }.body()
    }

    open suspend fun createTable(request: com.zamulabs.dineeasepos.data.dto.CreateTableRequestDto): com.zamulabs.dineeasepos.data.dto.TableResponseDto {
        return httpClient.post(urlString = "api/tables") {
            setBody(request)
        }.body()
    }

    // Users
    open suspend fun fetchUsers(): List<com.zamulabs.dineeasepos.data.dto.UserResponseDto> {
        return httpClient.get(urlString = "api/users") {
            parameter("all", 1)
        }.body()
    }

    open suspend fun createUser(request: com.zamulabs.dineeasepos.data.dto.CreateUserRequestDto): com.zamulabs.dineeasepos.data.dto.UserResponseDto {
        return httpClient.post(urlString = "api/users") {
            setBody(request)
        }.body()
    }

    // Reports
    open suspend fun fetchSalesReports(period: String?): List<com.zamulabs.dineeasepos.data.dto.SalesReportDto> {
        return httpClient.get(urlString = "api/reports/sales") {
            period?.let { parameter("period", it) }
        }.body()
    }

    // Receipt
    open suspend fun fetchReceipt(orderId: String): com.zamulabs.dineeasepos.data.dto.ReceiptDto {
        return httpClient.get(urlString = "api/receipts/$orderId").body()
    }

    // Payments
    open suspend fun createPayment(request: com.zamulabs.dineeasepos.data.dto.CreatePaymentRequestDto): com.zamulabs.dineeasepos.data.dto.PaymentResponseDto {
        return httpClient.post(urlString = "api/payments") { setBody(request) }.body()
    }
    open suspend fun fetchPayments(): List<com.zamulabs.dineeasepos.data.dto.PaymentListItemDto> {
        return httpClient.get(urlString = "api/payments").body()
    }

    open suspend fun fetchOrders(): List<com.zamulabs.dineeasepos.data.dto.OrderResponseDto> {
        return httpClient.get(urlString = "api/orders") { }.body()
    }

    // Auth
    open suspend fun login(request: com.zamulabs.dineeasepos.data.dto.LoginRequestDto): com.zamulabs.dineeasepos.data.dto.LoginResponseDto {
        return httpClient.post(urlString = "api/auth/login") { setBody(request) }.body()
    }

    open suspend fun forgotPassword(request: com.zamulabs.dineeasepos.data.dto.ForgotPasswordRequestDto): com.zamulabs.dineeasepos.data.dto.MessageResponseDto {
        return httpClient.post(urlString = "api/auth/forgot-password") { setBody(request) }.body()
    }

    open suspend fun resetPassword(request: com.zamulabs.dineeasepos.data.dto.ResetPasswordRequestDto): com.zamulabs.dineeasepos.data.dto.MessageResponseDto {
        return httpClient.post(urlString = "api/auth/reset-password") { setBody(request) }.body()
    }

    companion object {
        const val BASE_URL = ""
    }
}
