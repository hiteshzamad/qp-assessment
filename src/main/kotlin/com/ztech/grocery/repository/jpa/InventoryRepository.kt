package com.ztech.grocery.repository.jpa

import com.ztech.grocery.model.entity.Inventory
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface InventoryRepository : JpaRepository<Inventory, Int> {

    @EntityGraph("InventoryWithProduct")
    override fun findById(id: Int): Optional<Inventory>

    @Query("SELECT i.quantity FROM Inventory i WHERE i.id = :id")
    fun findQuantityById(@Param("id") id: Int): Optional<Int>

    @EntityGraph("InventoryWithProduct")
    fun findByProductId(
        @Param("productId") productId: Int
    ): Optional<Inventory>

    @EntityGraph("InventoryWithProduct")
    fun findByProductNameContainingIgnoreCase(name: String, pageRequest: PageRequest): List<Inventory>

    @Modifying
    @Query(
        "UPDATE Inventory i " +
                "SET i.quantity = i.quantity + :quantityChange " +
                "WHERE i.id = :inventoryId"
    )
    fun updateById(
        @Param("inventoryId") id: Int,
        @Param("quantityChange") quantityChange: Int
    )

    @Modifying
    @Query(
        "UPDATE Inventory i " +
                "SET i.quantity = i.quantity + :quantityChange, " +
                "i.price = :price " +
                "WHERE i.id = :inventoryId"
    )
    fun updateById(
        @Param("inventoryId") id: Int,
        @Param("quantityChange") quantityChange: Int,
        @Param("price") price: Double
    )
}