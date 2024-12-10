package com.ztech.grocery.model.domain

data class Inventory(
    val id: Int,
    val price: Double,
    val quantity: Int,
    val product: Product?
)
