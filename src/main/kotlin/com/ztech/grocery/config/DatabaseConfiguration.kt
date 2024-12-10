package com.ztech.grocery.config

import com.ztech.grocery.service.AccountServiceImpl
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class DatabaseConfiguration(
    private val accountService: AccountServiceImpl,
    private val secret: SecretConfiguration
) {
    @EventListener(ApplicationReadyEvent::class)
    fun createAdmin() {
        try {
            accountService.createAccount("administrator", secret.adminPassword, true)
        } catch (e: Exception) {
            println("Error Database Config : " + e.message)
        }
    }
}