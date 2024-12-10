package com.ztech.grocery.model

import com.ztech.grocery.model.domain.*

fun Account.toMap() = mapOf(
    "accountId" to this.id,
    "username" to this.username,
    "isAdmin" to this.isAdmin,
    "customer" to this.customer?.toMap(),
)

fun Customer.toMap() = mapOf(
    "customerId" to this.id,
    "name" to this.name,
    "mobile" to this.mobile,
)

fun Product.toMap() = mapOf(
    "productId" to this.id,
    "name" to this.name,
    "measure" to this.measure,
    "size" to this.size
)

fun Inventory.toMap() = mapOf(
    "inventoryId" to this.id,
    "quantity" to this.quantity,
    "price" to this.price,
    "product" to this.product?.toMap()
)

fun Cart.toMap() = mapOf(
    "cartId" to this.id,
    "quantity" to this.quantity,
    "inventory" to this.inventory?.toMap()
)

fun PurchaseItem.toMap() = mapOf(
    "purchaseItemId" to this.id,
    "quantity" to this.quantity,
    "price" to this.price,
    "product" to this.product?.toMap(),
)

fun Order.toMap() = mapOf(
    "orderId" to this.id,
    "customer" to this.customer,
    "purchaseItems" to this.purchaseItems?.map { it.toMap() },
)
