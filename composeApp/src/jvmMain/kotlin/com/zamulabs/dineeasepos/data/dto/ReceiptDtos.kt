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
package com.zamulabs.dineeasepos.data.dto

import com.zamulabs.dineeasepos.ui.receipt.ReceiptItem
import com.zamulabs.dineeasepos.ui.receipt.ReceiptUiState

/**
 * DTOs for Receipt feature and mappers to domain models, following Menu/Reports style.
 */

data class ReceiptItemDto(
    val item: String,
    val quantity: Int,
    val price: String,
    val total: String,
)

data class ReceiptDto(
    val orderId: String,
    val restaurantName: String,
    val address: String,
    val phone: String,
    val orderDate: String,
    val orderTime: String,
    val orderType: String,
    val items: List<ReceiptItemDto>,
    val subtotal: String,
    val tax: String,
    val total: String,
    val paymentMethod: String,
)

object ReceiptMappers {
    fun ReceiptItemDto.toDomain(): ReceiptItem = ReceiptItem(
        item = item,
        quantity = quantity,
        price = price,
        total = total,
    )

    fun ReceiptDto.toUiState(): ReceiptUiState = ReceiptUiState(
        orderId = orderId,
        restaurantName = restaurantName,
        address = address,
        phone = phone,
        orderDate = orderDate,
        orderTime = orderTime,
        orderType = orderType,
        items = items.map { it.toDomain() },
        subtotal = subtotal,
        tax = tax,
        total = total,
        paymentMethod = paymentMethod,
    )
}
