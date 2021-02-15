package com.jordansilva.kstore.domain.model

import java.util.Date
import java.util.UUID

data class Cart(
    private val id: String = UUID.randomUUID().toString(),
    private val _products: MutableMap<String, CartItem> = mutableMapOf(),
    private val createdAt: Long = Date().time
) {

    val products get() = _products.values.toList()

    fun add(item: Product) {
        val product = _products[item.id] ?: CartItem(item.id, item.name, item.image, 0, item.price.currency, item.price.value)
        product.quantity += 1
        _products[product.id] = product
    }

    fun remove(id: String) {
        _products[id]?.let { product ->
            product.quantity -= 1
            if (product.quantity > 0) {
                _products[product.id] = product
            } else {
                _products.remove(product.id)
            }
        }
    }

    fun removeAll(id: String) = _products.remove(id)
    fun quantityItems() = _products.values.sumOf { it.quantity }
    fun totalPrice() = _products.values.sumOf { it.totalPrice() }
}