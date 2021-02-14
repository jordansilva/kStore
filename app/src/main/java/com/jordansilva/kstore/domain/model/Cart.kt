package com.jordansilva.kstore.domain.model

class Cart {
    private val _products: MutableList<Product> = mutableListOf()
    val products: List<Product> get() = _products

    fun add(item: Product) { _products.add(item) }
    fun remove(id: String) { _products.removeAll { it.id == id } }
    fun total() = products.sumOf { it.price.value }

    override fun equals(other: Any?): Boolean {
        if (other !is Cart) return false
        if (this === other) return true

        return super.equals(other)
    }

    override fun hashCode(): Int {
        return _products.hashCode()
    }
}