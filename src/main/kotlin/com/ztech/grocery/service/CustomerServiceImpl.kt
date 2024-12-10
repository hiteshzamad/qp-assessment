package com.ztech.grocery.service

import com.ztech.grocery.exception.ResourceNotFoundException
import com.ztech.grocery.model.toDomain
import com.ztech.grocery.repository.jpa.CustomerRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.grocery.model.entity.Account as AccountEntity
import com.ztech.grocery.model.entity.Customer as CustomerEntity

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
) {
    fun createCustomer(accountId: Int, name: String, mobile: String) =
        customerRepository.save(CustomerEntity().also {
            it.account = AccountEntity(accountId)
            it.name = name
            it.mobile = mobile
        }).toDomain()

    fun getCustomersByAccountId(accountId: Int) =
        customerRepository.findByAccountId(accountId)
            .map { it.toDomain() }

    fun getCustomerByCustomerId(customerId: Int) =
        customerRepository.findById(customerId).getOrElse {
            throw ResourceNotFoundException("Customer not found")
        }.toDomain()

}