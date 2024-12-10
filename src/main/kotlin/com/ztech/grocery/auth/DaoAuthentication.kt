package com.ztech.grocery.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class DaoAuthentication(
    val aid: Int,
    private val username: String,
    private val password: String,
    val isAdmin: Boolean,
    val cid: Int?,
) : UserDetails {

    override fun getAuthorities(): List<GrantedAuthority> = listOf()

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}