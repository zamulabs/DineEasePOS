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

import com.zamulabs.dineeasepos.ui.table.DiningTable
import com.zamulabs.dineeasepos.ui.table.TableStatus

/**
 * DTOs for Table endpoints
 */

data class CreateTableRequestDto(
    val number: String,
    val name: String?,
    val capacity: Int,
    val location: String?,
)

data class TableResponseDto(
    val id: String?,
    val number: String,
    val capacity: Int,
    val status: String?,
)

object TableMappers {
    fun TableResponseDto.toDomain(): DiningTable =
        DiningTable(
            number = number,
            capacity = capacity,
            status = when (status?.lowercase()) {
                "occupied" -> TableStatus.Occupied
                else -> TableStatus.Available
            }
        )
}
