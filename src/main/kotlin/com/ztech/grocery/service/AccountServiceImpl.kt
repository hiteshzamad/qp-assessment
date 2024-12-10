package com.ztech.grocery.service

import com.ztech.grocery.exception.ResourceNotFoundException
import com.ztech.grocery.model.toDomain
import com.ztech.grocery.repository.jpa.AccountRepository
import com.ztech.grocery.util.CryptoAESUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.grocery.model.entity.Account as AccountEntity

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val cryptoPassword: CryptoAESUtil,
    private val passwordEncoder: PasswordEncoder
) {
    fun createAccount(
        username: String, password: String, isAdmin: Boolean = false
    ) = accountRepository.save(AccountEntity().also {
        it.isAdmin = isAdmin
        it.username = username
        it.password = cryptoPassword.encrypt(passwordEncoder.encode(password))
    }).toDomain(_customer = false)

    fun getAccountByAccountId(accountId: Int) =
        accountRepository.findById(accountId).getOrElse {
            throw ResourceNotFoundException("Account not found")
        }.toDomain()

}