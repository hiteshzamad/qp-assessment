package com.ztech.grocery.controller

import com.ztech.grocery.model.dto.CartCreateRequest
import com.ztech.grocery.model.dto.CartUpdateRequest
import com.ztech.grocery.model.response.Response
import com.ztech.grocery.model.response.responseSuccess
import com.ztech.grocery.model.toMap
import com.ztech.grocery.service.CartServiceImpl
import com.ztech.grocery.validator.ValidId
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers/{customerId}/carts")
@PreAuthorize("#customerId == authentication.principal.cid")
class CustomerCartController(
    private val cartService: CartServiceImpl
) {

    @PostMapping
    fun createCart(
        @PathVariable @ValidId customerId: Int,
        @RequestBody @Valid cart: CartCreateRequest
    ): ResponseEntity<Response> {
        val (inventoryId, quantity) = cart
        val response = cartService.createCart(customerId, inventoryId, quantity)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getCarts(
        @PathVariable @ValidId customerId: Int,
    ): ResponseEntity<Response> {
        val response = cartService.getCartsByCustomerId(customerId)
        return responseSuccess(mapOf("carts" to response.map { it.toMap() }))
    }

    @GetMapping("/{cartId}")
    fun getCart(
        @PathVariable @ValidId customerId: Int,
        @PathVariable @ValidId cartId: Int
    ): ResponseEntity<Response> {
        val response = cartService.getCartByCartIdAndCustomerId(cartId, customerId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{cartId}")
    fun updateCart(
        @PathVariable @ValidId customerId: Int,
        @PathVariable @ValidId cartId: Int,
        @RequestBody @Valid cart: CartUpdateRequest
    ): ResponseEntity<Response> {
        val (quantityChange) = cart
        cartService.updateCartByCartIdAndCustomerId(cartId, customerId, quantityChange)
        return responseSuccess()
    }

    @DeleteMapping("/{cartId}")
    fun deleteCart(
        @PathVariable @ValidId customerId: Int,
        @PathVariable @ValidId cartId: Int,
    ): ResponseEntity<Response> {
        cartService.deleteCartByCartIdAndCustomerId(cartId, customerId)
        return responseSuccess()
    }

}
