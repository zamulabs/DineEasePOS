package com.zamulabs.dineeasepos

import com.zamulabs.dineeasepos.menu.MenuManagementViewModel
import com.zamulabs.dineeasepos.menu.addmenu.AddMenuItemViewModel
import com.zamulabs.dineeasepos.table.TableManagementViewModel
import com.zamulabs.dineeasepos.table.addtable.AddTableViewModel
import com.zamulabs.dineeasepos.dashboard.DashboardViewModel
import com.zamulabs.dineeasepos.order.neworder.NewOrderViewModel
import com.zamulabs.dineeasepos.order.OrderManagementViewModel
import com.zamulabs.dineeasepos.order.details.OrderDetailsViewModel
import com.zamulabs.dineeasepos.table.details.TableDetailsViewModel
import com.zamulabs.dineeasepos.payment.PaymentProcessingViewModel
import com.zamulabs.dineeasepos.settings.SettingsViewModel
import com.zamulabs.dineeasepos.user.UserManagementViewModel
import com.zamulabs.dineeasepos.user.adduser.AddUserViewModel
import com.zamulabs.dineeasepos.receipt.ReceiptViewModel
import com.zamulabs.dineeasepos.auth.login.LoginViewModel

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule = module {
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
    singleOf(::SettingsViewModel)
    singleOf(::UserManagementViewModel)
    singleOf(::AddUserViewModel)
    singleOf(::ReceiptViewModel)
    singleOf(::LoginViewModel)
}