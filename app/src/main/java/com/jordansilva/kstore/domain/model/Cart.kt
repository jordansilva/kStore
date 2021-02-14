package com.jordansilva.kstore.domain.model

import java.math.BigDecimal

data class Cart(private val _products: MutableMap<String, CartProduct> = mutableMapOf()) {

    val products get() = _products.values.toList()

    data class CartProduct(val id: String, val currency: String, val price: BigDecimal, var quantity: Int) {
        fun totalPrice() = price.times(quantity.toBigDecimal())
    }

    fun add(item: Product) {
        val product = _products[item.id] ?: CartProduct(item.id, item.price.currency, item.price.value, 0)
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