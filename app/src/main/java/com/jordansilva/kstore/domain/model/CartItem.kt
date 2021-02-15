package com.jordansilva.kstore.domain.model

import java.math.BigDecimal

data class CartItem(
    val id: String,
    val name: String,
    val image: String?,
    var quantity: Int,
    val currency: String,
    val unitPrice: BigDecimal
) {
    fun totalPrice() = unitPrice.times(quantity.toBigDecimal())
}