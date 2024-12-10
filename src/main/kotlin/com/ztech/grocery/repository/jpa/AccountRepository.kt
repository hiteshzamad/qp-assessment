package com.ztech.grocery.repository.jpa

import com.ztech.grocery.model.entity.Account
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, Int> {

    @EntityGraph("AccountWithCustomer")
    override fun findById(id: Int): Optional<Account>

    @EntityGraph("AccountWithCustomer")
    fun findByUsername(username: String): Optional<Account>
}