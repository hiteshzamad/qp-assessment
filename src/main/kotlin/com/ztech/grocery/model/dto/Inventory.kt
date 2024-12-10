package com.ztech.grocery.model.dto

import com.ztech.grocery.validator.ValidId
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class InventoryCreateRequest(
    @field:ValidId(message = "Invalid product id")
    val productId: Int,
    @field:Min(1, message = "Price should be greater than 0")
    val price: Double,
    @field:Min(1, message = "Quantity should be greater than 0")
    val quantity: Int
)

data class InventoryUpdateRequest(
    @field:Min(1, message = "Price should be greater than 0")
    val price: Double,
    @field:NotNull()
    val quantityChange: Int
)