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

import com.zamulabs.dineeasepos.ui.menu.MenuItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuResponseDto(
    @SerialName("name")
    val name: String?,
    @SerialName("category")
    val category: String?,
    @SerialName("price")
    val price: String?,
    @SerialName("active")
    val active: Boolean?,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
) {
    companion object {
        fun MenuResponseDto.toDomainModel(): MenuItem {
            return MenuItem(
                name = name.orEmpty(),
                category = category.orEmpty(),
                price = price.orEmpty(),
                active = active ?: false,
                imageUrl = imageUrl
            )
        }
    }
}
