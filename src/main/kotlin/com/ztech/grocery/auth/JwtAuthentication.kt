package com.ztech.grocery.auth

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class JwtAuthentication(
    private var authenticated: Boolean,
    isAdmin: Boolean, accountId: Int, username: String, customerId: Int?,
) : Authentication {

    private val principal = UserPrincipal(accountId, isAdmin, username, null, customerId)
    override fun getName() = principal.username

    override fun getAuthorities() = listOf<GrantedAuthority>()

    override fun getCredentials() = null

    override fun getDetails() = null

    override fun getPrincipal() = principal

    override fun isAuthenticated() = authenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }
}