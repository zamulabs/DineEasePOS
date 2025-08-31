package com.zamulabs.dineeasepos.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object Login

    @Serializable
    object Order

    @Serializable
    object Menu

    // Payments list screen (analytics/history)
    @Serializable
    object Payment

    // Payment processing flow for a specific order
    @Serializable
    object PaymentProcessing

    @Serializable
    object Table

    @Serializable
    object AddTable

    @Serializable
    object AddMenuItem

    @Serializable
    object Dashboard

    @Serializable
    object NewOrder

    @Serializable
    object OrderDetails

    @Serializable
    object TableDetails

    @Serializable
    object Settings

    @Serializable
    object Users

    @Serializable
    object AddUser

    @Serializable
    object Reports

    @Serializable
    object Receipt
}
