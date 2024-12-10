package com.ztech.grocery.model.domain

import java.time.LocalDateTime

data class Account(
    val id: Int,
    val username: String,
    val password: String,
    val isAdmin: Boolean,
    val createdAt: LocalDateTime,
    val customer: Customer?,
)
