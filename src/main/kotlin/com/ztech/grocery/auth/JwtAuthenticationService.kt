package com.ztech.grocery.auth

import com.ztech.grocery.exception.ExpiredTokenException
import com.ztech.grocery.exception.InvalidTokenException
import com.ztech.grocery.util.JwtUtil
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.core.Authentication

class JwtAuthenticationService(
    private val jwtUtil: JwtUtil
) : BearerAuthenticationService {

    override fun authenticate(token: String): Authentication {
        try {
            val claim = jwtUtil.getClaims(token)
            return JwtAuthentication(
                authenticated = true,
                username = claim["username"] as String,
                accountId = claim["accountId"] as Int,
                customerId = claim["customerId"] as Int?,
                isAdmin = claim["isAdmin"] as Boolean
            )
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException()
        } catch (e: Exception) {
            println(e.message)
            throw InvalidTokenException()
        }
    }
}