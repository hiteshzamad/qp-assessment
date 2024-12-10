package com.ztech.grocery.controller

import com.ztech.grocery.model.dto.AccountCreateRequest
import com.ztech.grocery.model.response.Response
import com.ztech.grocery.model.response.responseSuccess
import com.ztech.grocery.model.toMap
import com.ztech.grocery.service.AccountServiceImpl
import com.ztech.grocery.validator.ValidId
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountService: AccountServiceImpl
) {

    @PostMapping
    fun createAccount(@RequestBody @Valid account: AccountCreateRequest): ResponseEntity<Response> {
        val (username, password) = account
        val response = accountService.createAccount(username, password)
        return responseSuccess(response.toMap())
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("#accountId == authentication.principal.aid")
    fun getAccount(@PathVariable @ValidId accountId: Int): ResponseEntity<Response> {
        val response = accountService.getAccountByAccountId(accountId)
        return responseSuccess(response.toMap())
    }

}