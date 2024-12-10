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
@RequestMapping("/orders")
@PreAuthorize("authentication.principal.isAdmin")
class OrderController(
    private val checkoutService: CheckoutServiceImpl,
    private val orderService: OrderServiceImpl
) {

    @GetMapping
    fun getOrders(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Response> {
        val response = orderService.getOrders(page, pageSize)
        return responseSuccess(mapOf("orders" to response.map { it.toMap() }))
    }

    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable @ValidId orderId: Int
    ): ResponseEntity<Response> {
        val response = orderService.getOrderByOrderId(orderId)
        return responseSuccess(response.toMap())
    }

}