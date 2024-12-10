package com.ztech.grocery.model.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "purchase_item", uniqueConstraints = [
        UniqueConstraint(name = "unicst_order_product", columnNames = ["order_id", "product_id"])
    ]
)
data class PurchaseItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_item_id")
    val id: Int? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    lateinit var order: Order

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    lateinit var product: Product

    @Column(name = "price", precision = 10, scale = 2, nullable = false, updatable = false)
    lateinit var price: BigDecimal

    @Column(name = "quantity", nullable = false, updatable = false)
    var quantity: Int = 0
}
