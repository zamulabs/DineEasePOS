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
import com.zamulabs.dineeasepos.ui.order.Order
import com.zamulabs.dineeasepos.ui.order.OrderStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class OrderResponseDto(
    @SerialName("id") val id: String? = null,
    @SerialName("table") val table: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("total") val total: Double? = null,
    @SerialName("time") val time: String? = null,
)

object OrderMappers {
    fun OrderResponseDto.toDomain(): Order = Order(
        id = id.orEmpty(),
        table = table.orEmpty(),
        status = when (status?.lowercase()) {
            "open", "pending", "in_progress" -> OrderStatus.Open
            "completed", "closed", "paid" -> OrderStatus.Completed
            else -> OrderStatus.Open
        },
        total = "$" + String.format("%.2f", total ?: 0.0),
        time = time.orEmpty(),
    )
}
