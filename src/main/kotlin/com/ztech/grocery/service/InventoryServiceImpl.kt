package com.ztech.grocery.service

import com.ztech.grocery.component.TransactionHandler
import com.ztech.grocery.exception.RequestInvalidException
import com.ztech.grocery.exception.ResourceNotFoundException
import com.ztech.grocery.model.toDomain
import com.ztech.grocery.repository.jpa.InventoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.grocery.model.domain.Inventory as InventoryDomain
import com.ztech.grocery.model.entity.Inventory as InventoryEntity
import com.ztech.grocery.model.entity.Product as ProductEntity

@Service
class InventoryServiceImpl(
    private val inventoryRepository: InventoryRepository,
    private val cartService: CartServiceImpl,
    private val transactionHandler: TransactionHandler
) {
    fun createInventory(productId: Int, quantity: Int, price: Double) =
        inventoryRepository.save(InventoryEntity().also { inventory ->
            inventory.product = ProductEntity(productId)
            inventory.quantity = quantity
            inventory.price = price.toBigDecimal()
        }).toDomain(_product = false)

    fun getInventoriesByProductName(name: String, page: Int, pageSize: Int) =
        inventoryRepository.findByProductNameContainingIgnoreCase(name, PageRequest.of(page, pageSize))
            .map { it.toDomain() }

    fun getInventoryByInventoryId(inventoryId: Int) =
        inventoryRepository.findById(inventoryId).getOrElse {
            throw ResourceNotFoundException("Inventory not found")
        }.toDomain()

    fun updateInventoryByInventoryId(
        inventoryId: Int, quantityChange: Int, price: Double
    ) = this.transactionHandler.execute {
        val quantity = inventoryRepository.findQuantityById(inventoryId).getOrElse {
            throw ResourceNotFoundException("Inventory not found")
        }
        val newQuantity = quantity + quantityChange
        when {
            newQuantity >= 0 ->
                inventoryRepository.updateById(inventoryId, quantityChange, price)

            else -> throw RequestInvalidException("Quantity cannot be negative")
        }
    }

    fun updateQuantityByInventory(
        inventory: InventoryDomain, quantityChange: Int
    ) = this.transactionHandler.execute {
        val newQuantity = inventory.quantity + quantityChange
        when {
            newQuantity >= 0 ->
                inventoryRepository.updateById(inventory.id, quantityChange)

            else -> throw RequestInvalidException("Inventory not enough")
        }
    }

    fun deleteInventory(inventoryId: Int) = this.transactionHandler.execute {
        cartService.deleteCartByInventoryId(inventoryId)
        inventoryRepository.deleteById(inventoryId)
    }

}