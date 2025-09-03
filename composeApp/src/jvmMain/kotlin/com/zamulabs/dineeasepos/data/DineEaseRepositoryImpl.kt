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
}
