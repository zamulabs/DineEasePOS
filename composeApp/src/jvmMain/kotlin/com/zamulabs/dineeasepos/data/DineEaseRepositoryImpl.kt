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

    override suspend fun getOrders(): NetworkResult<List<Order>> {
        // TODO: Replace with real API call when available, e.g., apiService.fetchOrders()
        return safeApiCall {
            Napier.e("Fetching orders")
            // Provide empty list; ViewModel will fallback to sample data until API is ready.
            emptyList()
        }
    }

    override suspend fun getTables(): NetworkResult<List<com.zamulabs.dineeasepos.ui.table.DiningTable>> {
        // TODO: Replace with real API call when available, e.g., apiService.fetchTables()
        return safeApiCall {
            Napier.e("Fetching tables")
            emptyList()
        }
    }

    override suspend fun getPayments(): NetworkResult<List<PaymentItem>> {
        // TODO: Replace with real API call when available, e.g., apiService.fetchPayments()
        return safeApiCall {
            Napier.e("Fetching payments")
            emptyList()
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
}
