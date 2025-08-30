package com.zamulabs.dineeasepos.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object Login

    @Serializable
    object Order

    @Serializable
    object Menu

    @Serializable
    object Payment

    @Serializable
    object Table

    @Serializable
    object Dashboard
}
