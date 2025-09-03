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
package com.zamulabs.dineeasepos.di

import com.zamulabs.dineeasepos.data.ApiService
import com.zamulabs.dineeasepos.data.DineEaseRepository
import com.zamulabs.dineeasepos.data.DineEaseRepositoryImpl
import com.zamulabs.dineeasepos.data.SettingsRepositoryImpl
import com.zamulabs.dineeasepos.data.SettingsRepository
import com.zamulabs.dineeasepos.data.FakeApiService
import com.zamulabs.dineeasepos.ui.dashboard.DashboardViewModel
import com.zamulabs.dineeasepos.ui.login.LoginViewModel
import com.zamulabs.dineeasepos.ui.menu.MenuManagementViewModel
import com.zamulabs.dineeasepos.ui.menu.addmenu.AddMenuItemViewModel
import com.zamulabs.dineeasepos.ui.order.OrderManagementViewModel
import com.zamulabs.dineeasepos.ui.order.details.OrderDetailsViewModel
import com.zamulabs.dineeasepos.ui.order.neworder.NewOrderViewModel
import com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentProcessingViewModel
import com.zamulabs.dineeasepos.ui.payment.PaymentsViewModel
import com.zamulabs.dineeasepos.ui.receipt.ReceiptViewModel
import com.zamulabs.dineeasepos.ui.reports.ReportsViewModel
import com.zamulabs.dineeasepos.ui.settings.SettingsViewModel
import com.zamulabs.dineeasepos.ui.table.TableManagementViewModel
import com.zamulabs.dineeasepos.ui.table.addtable.AddTableViewModel
import com.zamulabs.dineeasepos.ui.table.details.TableDetailsViewModel
import com.zamulabs.dineeasepos.ui.user.UserManagementViewModel
import com.zamulabs.dineeasepos.ui.user.adduser.AddUserViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule =
    module {
        singleOf(::MenuManagementViewModel)
        singleOf(::TableManagementViewModel)
        singleOf(::DashboardViewModel)
        singleOf(::AddTableViewModel)
        singleOf(::AddMenuItemViewModel)
        singleOf(::NewOrderViewModel)
        singleOf(::OrderManagementViewModel)
        singleOf(::OrderDetailsViewModel)
        singleOf(::TableDetailsViewModel)
        singleOf(::PaymentProcessingViewModel)
        singleOf(::PaymentsViewModel)
        singleOf(::SettingsViewModel)
        singleOf(::UserManagementViewModel)
        singleOf(::AddUserViewModel)
        singleOf(::ReceiptViewModel)
        singleOf(::ReportsViewModel)
        singleOf(::LoginViewModel)

        /**
         * API Service (mocked)
         */
        single<ApiService> { FakeApiService(httpClient = get()) }

        single<SettingsRepository> { SettingsRepositoryImpl(preferenceManager = get()) }
        single<DineEaseRepository> { DineEaseRepositoryImpl(apiService = get()) }
    }
