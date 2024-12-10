package com.ztech.grocery.controller

import com.ztech.grocery.model.dto.InventoryCreateRequest
import com.ztech.grocery.model.dto.InventoryUpdateRequest
import com.ztech.grocery.model.response.Response
import com.ztech.grocery.model.response.responseSuccess
import com.ztech.grocery.model.toMap
import com.ztech.grocery.service.InventoryServiceImpl
import com.ztech.grocery.validator.ValidId
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inventories")
class InventoryController(
    private val inventoryService: InventoryServiceImpl
) {

    @GetMapping
    @PreAuthorize("authentication.principal.isAdmin || authentication.principal.cid != null")
    fun getInventories(
        @RequestParam(defaultValue = "") name: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Response> {
        val response = inventoryService.getInventoriesByProductName(name, page, pageSize)
        return responseSuccess(mapOf("inventories" to response.map { it.toMap() }))
    }

    @GetMapping("/{inventoryId}")
    @PreAuthorize("authentication.principal.isAdmin || authentication.principal.cid != null")
    fun getInventory(
        @PathVariable @ValidId inventoryId: Int
    ): ResponseEntity<Response> {
        val response = inventoryService.getInventoryByInventoryId(inventoryId)
        return responseSuccess(response.toMap())
    }

    @PostMapping
    @PreAuthorize("authentication.principal.isAdmin")
    fun createInventory(
        @RequestBody @Valid inventory: InventoryCreateRequest
    ): ResponseEntity<Response> {
        val (productId, price, quantity) = inventory
        val response = inventoryService.createInventory(productId, quantity, price)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{inventoryId}")
    @PreAuthorize("authentication.principal.isAdmin")
    fun updateInventory(
        @PathVariable @ValidId inventoryId: Int,
        @RequestBody @Valid inventory: InventoryUpdateRequest
    ): ResponseEntity<Response> {
        val (price, quantityChange) = inventory
        inventoryService.updateInventoryByInventoryId(inventoryId, quantityChange, price)
        return responseSuccess()
    }

    @DeleteMapping("/{inventoryId}")
    @PreAuthorize("authentication.principal.isAdmin")
    fun deleteInventory(
        @PathVariable @ValidId inventoryId: Int,
    ): ResponseEntity<Response> {
        inventoryService.deleteInventory(inventoryId)
        return responseSuccess()
    }

}