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

import androidx.compose.runtime.Immutable
import com.zamulabs.dineeasepos.ui.payment.PaymentItem
import com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentMethod

@Immutable
data class CreatePaymentRequestDto(
    val orderId: String,
    val method: String,
    val amountReceived: Double?,
    val gateway: String?,
)

@Immutable
data class PaymentResponseDto(
    val id: String,
    val orderId: String,
    val status: String,
    val method: String,
    val totalPaid: Double,
    val createdAt: String,
)

@Immutable
data class PaymentListItemDto(
    val date: String,
    val orderId: String,
    val method: String,
    val amount: Double,
)

object PaymentMappers {
    fun PaymentResponseDto.toProcessingStatus(): String = status
    fun PaymentListItemDto.toDomain(): PaymentItem = PaymentItem(
        date = date,
        orderId = orderId,
        method = method,
        amount = "${'$'}" + String.format("%.2f", amount)
    )
    fun PaymentMethod.toDto(): String = when (this) {
        PaymentMethod.Cash -> "cash"
        PaymentMethod.Online -> "online"
    }
}
