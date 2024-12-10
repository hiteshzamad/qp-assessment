package com.ztech.grocery.repository.jpa

import com.ztech.grocery.model.entity.PurchaseItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseItemRepository : JpaRepository<PurchaseItem, Int>