package com.ztech.grocery.auth

import com.ztech.grocery.repository.jpa.AccountRepository
import com.ztech.grocery.util.CryptoAESUtil
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class DaoAuthenticationService(
    private val accountRepository: AccountRepository,
    private val cryptoPassword: CryptoAESUtil
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val optional = accountRepository.findByUsername(username)
        if (optional.isPresent) {
            val account = optional.get()
            return DaoAuthentication(
                aid = account.id!!,
                isAdmin = account.isAdmin,
                username = account.username,
                password = cryptoPassword.decrypt(account.password),
                cid = if (!account.isAdmin) account.customer?.id else null,
            )
        } else {
            throw UsernameNotFoundException("Invalid username or password")
        }
    }
}