package com.ztech.grocery.service

import com.ztech.grocery.component.TransactionHandler
import com.ztech.grocery.exception.ResourceNotFoundException
import com.ztech.grocery.model.common.Measure
import com.ztech.grocery.model.toDomain
import com.ztech.grocery.repository.jpa.OrderRepository
import com.ztech.grocery.repository.jpa.PurchaseItemRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.grocery.model.domain.Cart as CartDomain
import com.ztech.grocery.model.entity.Customer as CustomerEntity
import com.ztech.grocery.model.entity.Order as OrderEntity
import com.ztech.grocery.model.entity.Product as ProductEntity
import com.ztech.grocery.model.entity.PurchaseItem as PurchaseItemEntity


@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val purchaseItemRepository: PurchaseItemRepository,
    private val transactionHandler: TransactionHandler
) {

    fun createOrder(
        customerId: Int,
        carts: List<CartDomain>
    ) = transactionHandler.execute {
        val order = orderRepository.save(OrderEntity().also {
            it.customer = CustomerEntity(customerId)
        })
        val purchaseItems = purchaseItemRepository.saveAll(
            carts.map { cart ->
                PurchaseItemEntity().also { item ->
                    item.order = order
                    item.quantity = cart.quantity
                    item.price = cart.inventory!!.price.toBigDecimal()
                    item.product = ProductEntity(cart.inventory.product!!.id).also {
                        it.name = cart.inventory.product.name
                        it.measure = Measure.valueOf(cart.inventory.product.measure.uppercase())
                        it.size = cart.inventory.product.size.toBigDecimal()
                    }
                }
            }
        )
        order.also {
            it.purchaseItems = purchaseItems.toMutableSet()
        }.toDomain()
    }

    fun getOrderByOrderId(orderId: Int) =
        orderRepository.findById(orderId).getOrElse {
            throw ResourceNotFoundException("Order not found")
        }.toDomain()


    fun getOrders(page: Int, pageSize: Int) =
        orderRepository.findAll(PageRequest.of(page, pageSize))
            .content.map { it.toDomain() }

    fun getOrdersByCustomerId(customerId: Int) =
        orderRepository.findByCustomerId(customerId)
            .map { it.toDomain(_customer = false) }

    fun getOrderByCustomerIdAndOrderId(customerId: Int, orderId: Int) =
        orderRepository.findByIdAndCustomerId(orderId, customerId).getOrElse {
            throw ResourceNotFoundException("Order not found")
        }.toDomain(_customer = false)

}
