package com.ztech.grocery.service

import com.ztech.grocery.exception.ResourceNotFoundException
import com.ztech.grocery.model.common.Measure
import com.ztech.grocery.model.toDomain
import com.ztech.grocery.repository.jpa.ProductRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.grocery.model.entity.Product as ProductEntity

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
) {
    fun createProduct(
        name: String, measure: String, size: Double
    ) = productRepository.save(ProductEntity().also {
        it.name = name
        it.measure = Measure.valueOf(measure.uppercase())
        it.size = size.toBigDecimal()
    }).toDomain()

    fun getProductsByName(name: String, page: Int, pageSize: Int) =
        productRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, pageSize))
            .map { it.toDomain() }

    fun getProduct(productId: Int) =
        productRepository.findById(productId).getOrElse {
            throw ResourceNotFoundException("Product not found")
        }.toDomain()

}