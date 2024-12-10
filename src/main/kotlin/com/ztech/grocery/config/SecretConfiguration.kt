package com.ztech.grocery.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SecretConfiguration {

    @Value("\${custom.key-password}")
    lateinit var passwordKey: String

    @Value("\${custom.key-token}")
    lateinit var tokenKey: String

    @Value("\${custom.admin-password}")
    lateinit var adminPassword: String

}