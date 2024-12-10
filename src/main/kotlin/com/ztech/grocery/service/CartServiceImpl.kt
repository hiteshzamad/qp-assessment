package com.ztech.grocery.service

import com.ztech.grocery.component.TransactionHandler
import com.ztech.grocery.exception.RequestInvalidException
import com.ztech.grocery.exception.ResourceNotFoundException
import com.ztech.grocery.model.toDomain
import com.ztech.grocery.repository.jpa.CartRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.grocery.model.entity.Cart as CartEntity
import com.ztech.grocery.model.entity.Customer as CustomerEntity
import com.ztech.grocery.model.entity.Inventory as InventoryEntity

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val transactionHandler: TransactionHandler
) {
    fun createCart(customerId: Int, inventoryId: Int, quantity: Int) = cartRepository.save(CartEntity().also { cart ->
        cart.customer = CustomerEntity(customerId)
        cart.inventory = InventoryEntity(inventoryId)
        cart.quantity = quantity
        if (quantity <= 0)
            throw RequestInvalidException("Quantity cannot be zero")
    }).toDomain(_inventory = false)

    fun getCartsByCustomerId(customerId: Int) =
        cartRepository.findByCustomerId(customerId).map { it.toDomain() }

    fun getCartByCartIdAndCustomerId(cartId: Int, customerId: Int) =
        cartRepository.findByIdAndCustomerId(cartId, customerId).getOrElse {
            throw ResourceNotFoundException("Cart not found")
        }.toDomain()

    fun updateCartByCartIdAndCustomerId(cartId: Int, customerId: Int, quantityChange: Int) =
        this.transactionHandler.execute {
            val quantity = cartRepository.findQuantityByIdAndCustomerId(cartId, customerId).getOrElse {
                throw ResourceNotFoundException("Cart not found")
            }
            val newQuantity = quantity + quantityChange
            when {
                newQuantity > 0 -> cartRepository.updateById(cartId, quantityChange)
                else -> throw RequestInvalidException("Quantity cannot be less than 0")
            }
        }

    fun deleteCartByCustomerId(customerId: Int) = this.transactionHandler.execute {
        cartRepository.deleteByCustomerId(customerId)
    }

    fun deleteCartByCartIdAndCustomerId(cartId: Int, customerId: Int) = this.transactionHandler.execute {
        cartRepository.deleteByIdAndCustomerId(cartId, customerId)
    }

    fun deleteCartByInventoryId(inventoryId: Int) = this.transactionHandler.execute {
        cartRepository.deleteByInventoryId(inventoryId)
    }
}