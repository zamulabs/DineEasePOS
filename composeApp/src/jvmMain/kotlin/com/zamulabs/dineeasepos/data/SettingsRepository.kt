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

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun saveBearerToken(token: String?)
    fun getBearerToken(): Flow<String?>

    suspend fun saveAppTheme(theme: Int)
    fun getAppTheme(): Flow<Int?>

    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean?>
    fun saveUserString(key: String, value: String?)

    suspend fun clearAll()
    fun getFirebaseToken(): Flow<String?>
    fun saveFirebaseToken(token: String)

    fun notificationEnabled(): Flow<Boolean>
    fun setNotificationEnabled(enabled: Boolean)
}
