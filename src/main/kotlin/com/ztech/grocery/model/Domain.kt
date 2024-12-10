package com.ztech.grocery.model

import com.ztech.grocery.model.entity.*

fun Account.toDomain(_customer: Boolean = true) = com.ztech.grocery.model.domain.Account(
    id = id!!,
    username = this.username,
    password = this.password,
    createdAt = this.createdAt,
    isAdmin = this.isAdmin,
    customer = if (_customer) customer?.toDomain() else null,
)

fun Customer.toDomain() = com.ztech.grocery.model.domain.Customer(
    id = id!!,
    name = name,
    mobile = mobile
)

fun Product.toDomain() = com.ztech.grocery.model.domain.Product(
    id = id!!,
    name = name,
    measure = measure.name.lowercase(),
    size = size.toDouble()
)

fun Inventory.toDomain(_product: Boolean = true) = com.ztech.grocery.model.domain.Inventory(
    id = id!!,
    quantity = quantity,
    price = price.toDouble(),
    product = if (_product) this.product.toDomain() else null,
)

fun Cart.toDomain(_inventory: Boolean = true, _product: Boolean = true) =
    com.ztech.grocery.model.domain.Cart(
        id = id!!,
        quantity = quantity,
        inventory = if (_inventory) this.inventory.toDomain(_product) else null,
    )

fun PurchaseItem.toDomain(
    _product: Boolean = true
) = com.ztech.grocery.model.domain.PurchaseItem(
    id = id!!,
    quantity = quantity,
    price = price.toDouble(),
    product = if (_product) product.toDomain() else null,
)

fun Order.toDomain(
    _product: Boolean = true,
    _customer: Boolean = true,
    _item: Boolean = true,
) = com.ztech.grocery.model.domain.Order(
    id = id!!,
    createdAt = createdAt,
    customer = if(_customer) customer.toDomain() else null,
    purchaseItems = if (_item) purchaseItems.map { it.toDomain(_product) } else null,
)