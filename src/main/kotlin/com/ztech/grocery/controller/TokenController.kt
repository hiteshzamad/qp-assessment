package com.ztech.grocery.controller

import com.ztech.grocery.auth.DaoAuthentication
import com.ztech.grocery.exception.AuthenticationException
import com.ztech.grocery.model.response.Response
import com.ztech.grocery.model.response.responseSuccess
import com.ztech.grocery.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tokens")
class TokenController(
    private val jwtUtil: JwtUtil
) {

    @PostMapping
    fun create(authentication: Authentication?): ResponseEntity<Response> {
        if (authentication == null) throw AuthenticationException()
        val account = authentication.principal as DaoAuthentication
        val map = mapOf(
            "isAdmin" to account.isAdmin,
            "username" to account.username,
            "accountId" to account.aid,
            "customerId" to account.cid,
        )
        val token: String = jwtUtil.createToken(map)
        return responseSuccess(
            mapOf(
                "token" to token,
                "accountId" to account.aid
            )
        )
    }
}