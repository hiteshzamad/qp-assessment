package com.ztech.grocery.model.domain

data class Cart(
    val id: Int,
    val quantity: Int,
    val inventory: Inventory?,
)
