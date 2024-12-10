package com.ztech.grocery.model.entity

import jakarta.persistence.*

@Entity
@NamedEntityGraph(
    name = "CartWithInventory",
    attributeNodes = [
        NamedAttributeNode("inventory")
    ]
)
@NamedEntityGraph(
    name = "CartWithInventoryWithProduct",
    attributeNodes = [
        NamedAttributeNode("inventory", subgraph = "Product")
    ],
    subgraphs = [
        NamedSubgraph(
            name = "Product",
            attributeNodes = [NamedAttributeNode("product")]
        )
    ]
)
@Table(
    name = "cart", uniqueConstraints = [
        UniqueConstraint(name = "unicst_inventory_customer", columnNames = ["inventory_id", "customer_id"])
    ]
)
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    val id: Int? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    lateinit var customer: Customer

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false, updatable = false)
    lateinit var inventory: Inventory

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 0
}