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

import com.zamulabs.dineeasepos.ui.reports.SalesRow

/**
 * DTOs for Reports feature and mappers to domain models, following Menu feature style.
 */

data class SalesReportDto(
    val date: String,
    val orders: Int,
    val gross: String,
    val discounts: String,
    val net: String,
)

object ReportsMappers {
    fun SalesReportDto.toDomain(): SalesRow = SalesRow(
        date = date,
        orders = orders,
        gross = gross,
        discounts = discounts,
        net = net,
    )
}
