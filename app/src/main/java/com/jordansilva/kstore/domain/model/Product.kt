package com.jordansilva.kstore.domain.model

import java.math.BigDecimal

data class Product(
    val id: String,
    val name: String,
    val type: String,
    val price: Price,
    val info: Map<String, String> = mapOf(),
    val image: String? = null
) {
    data class Price(val value: BigDecimal, val currency: String)
}