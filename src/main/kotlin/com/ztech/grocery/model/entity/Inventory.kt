package com.ztech.grocery.model.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@NamedEntityGraph(
    name = "InventoryWithProduct",
    attributeNodes = [NamedAttributeNode("product")]
)
@Table(
    name = "inventory", uniqueConstraints = [
        UniqueConstraint(name = "unicst_product", columnNames = ["product_id"])
    ]
)
data class Inventory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    val id: Int? = null
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    lateinit var product: Product
    
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    lateinit var price: BigDecimal

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 0

}
