package com.ztech.grocery.config

import com.ztech.grocery.util.CryptoAESUtil
import com.ztech.grocery.util.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UtilConfiguration {

    @Bean
    fun cryptoAESUtil(
        secret: SecretConfiguration,
    ) = CryptoAESUtil(secret.passwordKey)

    @Bean
    fun jwtUtil(
        secret: SecretConfiguration,
    ) = JwtUtil(secret.tokenKey)

}