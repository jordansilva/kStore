package com.jordansilva.kstore.domain.model

class Cart(val products: MutableList<Product> = mutableListOf()) {

    fun total() = products.sumOf { it.price.value }
}