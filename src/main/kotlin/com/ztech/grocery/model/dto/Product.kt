package com.ztech.grocery.model.dto

import jakarta.validation.constraints.Min

data class ProductCreateRequest(
    val name: String,
    val measure: String,
    @field:Min(1, message = "Size should be greater than 0")
    val size: Double,
)