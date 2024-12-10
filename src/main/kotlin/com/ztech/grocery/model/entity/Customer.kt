package com.ztech.grocery.model.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "customer",
    uniqueConstraints = [
        UniqueConstraint(name = "unicst_account", columnNames = ["account_id"]),
        UniqueConstraint(name = "unicst_mobile", columnNames = ["mobile"]),
    ]
)
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    val id: Int? = null
) {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    lateinit var account: Account

    @Column(name = "name", length = 64, nullable = false, updatable = false)
    lateinit var name: String

    @Column(name = "mobile", length = 15)
    lateinit var mobile: String

}