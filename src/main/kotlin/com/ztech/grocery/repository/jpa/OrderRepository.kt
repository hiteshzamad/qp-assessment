package com.ztech.grocery.repository.jpa

import com.ztech.grocery.model.entity.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order, Int> {


    @EntityGraph("OrderWithCustomerWithPurchaseItemsWithProduct")
    override fun findById(id: Int): Optional<Order>

    @EntityGraph("OrderWithPurchaseItemsWithProduct")
    fun findByIdAndCustomerId(id: Int, customerId: Int): Optional<Order>

    @EntityGraph("OrderWithPurchaseItemsWithProduct")
    fun findByCustomerId(customerId: Int): List<Order>

    @EntityGraph("OrderWithCustomerWithPurchaseItemsWithProduct")
    override fun findAll(pageable: Pageable): Page<Order>

}
