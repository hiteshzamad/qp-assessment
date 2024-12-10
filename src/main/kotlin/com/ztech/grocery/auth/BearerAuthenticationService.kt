package com.ztech.grocery.auth

import org.springframework.security.core.Authentication

interface BearerAuthenticationService {
    fun authenticate(token: String): Authentication
}