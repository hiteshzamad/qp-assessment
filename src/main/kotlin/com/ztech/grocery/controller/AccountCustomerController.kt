package com.ztech.grocery.controller

import com.ztech.grocery.model.dto.CustomerCreateRequest
import com.ztech.grocery.model.response.Response
import com.ztech.grocery.model.response.responseSuccess
import com.ztech.grocery.model.toMap
import com.ztech.grocery.service.CustomerServiceImpl
import com.ztech.grocery.validator.ValidId
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts/{accountId}/customers")
@PreAuthorize("#accountId == authentication.principal.aid && !authentication.principal.isAdmin")
class AccountCustomerController(
    private val customerService: CustomerServiceImpl
) {

    @PostMapping
    fun createCustomer(
        @PathVariable @ValidId accountId: Int,
        @RequestBody @Valid customer: CustomerCreateRequest
    ): ResponseEntity<Response> {
        val (name, mobile) = customer
        val response = customerService.createCustomer(accountId, name, mobile)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getCustomers(
        @PathVariable @ValidId accountId: Int,
    ): ResponseEntity<Response> {
        val response = customerService.getCustomersByAccountId(accountId)
        return responseSuccess(mapOf("customers" to response.map { it.toMap() }))
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("#customerId == authentication.principal.cid")
    fun getCustomer(
        @PathVariable @ValidId customerId: Int
    ): ResponseEntity<Response> {
        val response = customerService.getCustomerByCustomerId(customerId)
        return responseSuccess(response.toMap())
    }

}