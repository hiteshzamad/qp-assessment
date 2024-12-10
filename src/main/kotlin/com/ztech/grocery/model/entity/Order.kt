package com.ztech.grocery.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
@NamedEntityGraph(
    name = "OrderWithPurchaseItemsWithProduct",
    attributeNodes = [
        NamedAttributeNode("purchaseItems", subgraph = "PurchaseItemsWithProduct"),
    ],
    subgraphs = [
        NamedSubgraph(
            name = "PurchaseItemsWithProduct",
            attributeNodes = [
                NamedAttributeNode("product"),
            ]
        )
    ]
)
@NamedEntityGraph(
    name="OrderWithCustomerWithPurchaseItemsWithProduct",
    attributeNodes = [
        NamedAttributeNode("customer"),
        NamedAttributeNode("purchaseItems", subgraph = "PurchaseItemsWithProduct"),
    ],
    subgraphs = [
        NamedSubgraph(
            name = "PurchaseItemsWithProduct",
            attributeNodes = [
                NamedAttributeNode("product"),
            ]
        )
    ]
)
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val id: Int? = null,
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    lateinit var customer: Customer

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var purchaseItems: MutableSet<PurchaseItem>

}
