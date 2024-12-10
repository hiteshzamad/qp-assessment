package com.ztech.grocery.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@NamedEntityGraph(
    name = "AccountWithCustomer",
    attributeNodes = [
        NamedAttributeNode("customer"),
    ]
)
@Table(
    name = "account", uniqueConstraints = [
        UniqueConstraint(name = "unicst_username", columnNames = ["username"]),
    ]
)
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    val id: Int? = null,
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Column(name = "username", length = 32, nullable = false, updatable = false)
    lateinit var username: String

    @Column(name = "password", length = 1024, nullable = false)
    lateinit var password: String

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    var customer: Customer? = null

    @Column(name = "isAdmin")
    var isAdmin = false

}
