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

class SettingsRepositoryImpl(
    private val preferenceManager: PreferenceManager,
) : SettingsRepository {
    override fun saveBearerToken(token: String?) {
        preferenceManager.setString(key = PreferenceManager.BEARER_TOKEN, value = token)
    }

    override fun getBearerToken(): Flow<String?> {
        return preferenceManager.getString(key = PreferenceManager.BEARER_TOKEN)
    }

    override suspend fun saveAppTheme(theme: Int) {
        preferenceManager.setInt(key = PreferenceManager.APP_THEME, value = theme)
    }

    override fun getAppTheme(): Flow<Int?> {
        return preferenceManager.getInt(key = PreferenceManager.APP_THEME)
    }

    override suspend fun saveOnBoardingState(completed: Boolean) {
        preferenceManager.setBoolean(
            key = PreferenceManager.ON_BOARDING_COMPLETED,
            value = completed,
        )
    }

    override fun readOnBoardingState(): Flow<Boolean?> {
        return preferenceManager.getBoolean(key = PreferenceManager.ON_BOARDING_COMPLETED)
    }

    override fun saveUserString(key: String, value: String?) {
        preferenceManager.setString(key = key, value = value)
    }

    override suspend fun clearAll() {
        clearUser()
        preferenceManager.clearPreferences()
        preferenceManager.settings.clear()
    }

    override fun getFirebaseToken(): Flow<String?> {
        return preferenceManager.getString(key = PreferenceManager.FIREBASE_TOKEN)
    }

    override fun saveFirebaseToken(token: String) {
        preferenceManager.setString(key = PreferenceManager.FIREBASE_TOKEN, value = token)
    }

    override fun notificationEnabled(): Flow<Boolean> {
        return preferenceManager.getBoolean(key = PreferenceManager.NOTIFICATION_ENABLED)
    }

    override fun setNotificationEnabled(enabled: Boolean) {
        preferenceManager.setBoolean(key = PreferenceManager.NOTIFICATION_ENABLED, value = enabled)
    }

    private fun clearUser() {
        saveUserString(PreferenceManager.FIREBASE_TOKEN, null)
        saveUserString(PreferenceManager.BEARER_TOKEN, null)
        saveUserString(PreferenceManager.TOKEN, null)
        saveUserString(PreferenceManager.ACCOUNT_STATUS, null)
        saveUserString(PreferenceManager.DOB, null)
        saveUserString(PreferenceManager.EMAIL, null)
        saveUserString(PreferenceManager.EMAIL_VERIFIED_AT, null)
        saveUserString(PreferenceManager.F_NAME, null)
        saveUserString(PreferenceManager.GENDER, null)
        saveUserString(PreferenceManager.ID, null)
        saveUserString(PreferenceManager.L_NAME, null)
        saveUserString(PreferenceManager.PHONE, null)
        saveUserString(PreferenceManager.USER_NAME, null)
        saveUserString(PreferenceManager.USER_TYPE, null)
    }
}
