package com.ztech.grocery.repository.jpa

import com.ztech.grocery.model.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Int> {
    fun findByAccountId(accountId: Int): List<Customer>
}