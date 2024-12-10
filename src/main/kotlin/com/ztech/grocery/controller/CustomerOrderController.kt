package com.ztech.grocery.controller

import com.ztech.grocery.model.response.Response
import com.ztech.grocery.model.response.responseSuccess
import com.ztech.grocery.model.toMap
import com.ztech.grocery.service.CheckoutServiceImpl
import com.ztech.grocery.service.OrderServiceImpl
import com.ztech.grocery.validator.ValidId
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers/{customerId}/orders")
@PreAuthorize("#customerId == authentication.principal.cid")
class CustomerOrderController(
    private val checkoutService: CheckoutServiceImpl,
    private val orderService: OrderServiceImpl
) {

    @PostMapping
    fun createOrder(
        @PathVariable @ValidId customerId: Int,
    ): ResponseEntity<Response> {
        val response = checkoutService.createOrder(customerId)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getOrders(
        @PathVariable @ValidId customerId: Int
    ): ResponseEntity<Response> {
        val response = orderService.getOrdersByCustomerId(customerId)
        return responseSuccess(mapOf("orders" to response.map { it.toMap() }))
    }

    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable @ValidId customerId: Int,
        @PathVariable @ValidId orderId: Int
    ): ResponseEntity<Response> {
        val response = orderService.getOrderByCustomerIdAndOrderId(customerId, orderId)
        return responseSuccess(response.toMap())
    }

}