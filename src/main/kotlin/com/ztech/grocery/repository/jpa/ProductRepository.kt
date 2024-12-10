package com.ztech.grocery.repository.jpa

import com.ztech.grocery.model.entity.Product
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Int> {
    fun findByNameContainingIgnoreCase(name: String, pageRequest: PageRequest): List<Product>
}
