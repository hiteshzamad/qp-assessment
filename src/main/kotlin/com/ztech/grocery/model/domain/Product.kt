package com.ztech.grocery.model.domain

data class Product(
    val id: Int,
    val name: String,
    val measure: String,
    val size: Double,
)