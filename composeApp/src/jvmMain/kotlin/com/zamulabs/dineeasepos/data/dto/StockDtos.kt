/*
 * Copyright 2025 Zamulabs.
 */
package com.zamulabs.dineeasepos.data.dto

data class StockMovementDto(
    val item: String,
    val prepared: String,
    val sold: String,
    val remaining: String,
)
