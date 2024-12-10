package com.ztech.grocery.service

import com.ztech.grocery.component.TransactionHandler
import com.ztech.grocery.exception.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class CheckoutServiceImpl(
    private val cartService: CartServiceImpl,
    private val inventoryService: InventoryServiceImpl,
    private val orderService: OrderServiceImpl,
    private val transactionHandler: TransactionHandler
) {

    fun createOrder(customerId: Int) = this.transactionHandler.execute {
        val carts = cartService.getCartsByCustomerId(customerId).ifEmpty {
            throw ResourceNotFoundException("Cart is empty")
        }
        carts.forEach { cart ->
            inventoryService.updateQuantityByInventory(cart.inventory!!, -1 * cart.quantity)
        }
        cartService.deleteCartByCustomerId(customerId)
        orderService.createOrder(customerId, carts)
    }
}