package com.ztech.grocery.auth

open class UserPrincipal(
    val aid: Int,
    val isAdmin: Boolean,
    val username: String,
    val password: String?,
    val cid: Int?,
)