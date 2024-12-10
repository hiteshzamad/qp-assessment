package com.ztech.grocery.controller

import com.ztech.grocery.model.dto.ProductCreateRequest
import com.ztech.grocery.model.response.Response
import com.ztech.grocery.model.response.responseSuccess
import com.ztech.grocery.model.toMap
import com.ztech.grocery.validator.ValidId
import com.ztech.grocery.service.ProductServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductServiceImpl
) {

    @PostMapping
    @PreAuthorize("authentication.principal.isAdmin")
    fun createProduct(
        @RequestBody @Valid product: ProductCreateRequest
    ): ResponseEntity<Response> {
        val (name, measure, size) = product
        val response = productService.createProduct(name, measure, size)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    @PreAuthorize("authentication.principal.isAdmin || authentication.principal.cid != null")
    fun getProducts(
        @RequestParam(defaultValue = "") name: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Response> {
        val response = productService.getProductsByName(name, page, pageSize)
        return responseSuccess(mapOf("products" to response.map { it.toMap() }))
    }

    @GetMapping("/{productId}")
    @PreAuthorize("authentication.principal.isAdmin || authentication.principal.cid != null")
    fun getProduct(
        @PathVariable @ValidId productId: Int
    ): ResponseEntity<Response> {
        val response = productService.getProduct(productId)
        return responseSuccess(response.toMap())
    }

}