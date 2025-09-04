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

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getBooleanFlow
import com.russhwolf.settings.coroutines.getIntOrNullFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow

class PreferenceManager(val settings: Settings) {
    private val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }

    fun setString(key: String, value: String?) {
        observableSettings.set(key = key, value = value)
    }

    @OptIn(ExperimentalSettingsApi::class)
    fun getString(key: String) = observableSettings.getStringOrNullFlow(key = key)

    fun setInt(key: String, value: Int) {
        observableSettings.set(key = key, value = value)
    }

    @OptIn(ExperimentalSettingsApi::class)
    fun getInt(key: String) = observableSettings.getIntOrNullFlow(key = key)

    companion object {
        const val BEARER_TOKEN = "bearer_token_key"
        const val APP_THEME = "app_theme_key"

        // User
        const val TOKEN = "token_key"
        const val ACCOUNT_STATUS = "account_status_key"
        const val DOB = "dob_key"
        const val EMAIL = "email_key"
        const val EMAIL_VERIFIED_AT = "email_verified_at_key"
        const val F_NAME = "f_name_key"
        const val GENDER = "gender_key"
        const val ID = "id_key"
        const val L_NAME = "l_name_key"
        const val PHONE = "phone_key"
        const val USER_NAME = "user_name_key"
        const val USER_TYPE = "user_type_key"

        const val NOTIFICATION_ENABLED = "notification_enabled_key"
        const val ON_BOARDING_COMPLETED = "on_boarding_completed_key"
        const val FIREBASE_TOKEN = "firebase_token_key"
        const val PASSWORD_RESET_REQUIRED = "password_reset_required_key"
        const val SUPER_ADMIN_DEV_OVERRIDE = "super_admin_dev_override"
        const val FIRST_LOGIN_EMAIL = "first_login_email_key"
    }

    fun clearPreferences() {
        observableSettings.clear()
    }

    @OptIn(ExperimentalSettingsApi::class)
    fun getBoolean(key: String): Flow<Boolean> {
        return observableSettings.getBooleanFlow(
            key = key,
            defaultValue = false,
        )
    }

    fun setBoolean(key: String, value: Boolean) {
        observableSettings.set(key = key, value = value)
    }
}
