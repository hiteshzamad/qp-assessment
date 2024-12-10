package com.ztech.grocery.model.domain

import java.time.LocalDateTime

data class Order(
    val id: Int,
    val createdAt: LocalDateTime,
    val customer: Customer?,
    val purchaseItems: List<PurchaseItem>?,
)