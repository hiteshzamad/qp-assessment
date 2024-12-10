package com.ztech.grocery.controller

import com.ztech.grocery.model.response.Response
import com.ztech.grocery.model.response.responseSuccess
import com.ztech.grocery.model.toMap
import com.ztech.grocery.service.CustomerServiceImpl
import com.ztech.grocery.validator.ValidId
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerService: CustomerServiceImpl
) {

    @GetMapping("/{customerId}")
    @PreAuthorize("#customerId == authentication.principal.cid")
    fun getCustomer(
        @PathVariable @ValidId customerId: Int
    ): ResponseEntity<Response> {
        val response = customerService.getCustomerByCustomerId(customerId)
        return responseSuccess(response.toMap())
    }

}