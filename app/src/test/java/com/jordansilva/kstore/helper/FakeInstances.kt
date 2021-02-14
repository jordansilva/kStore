package com.jordansilva.kstore.helper

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.model.Product
import java.math.BigDecimal

object FakeInstances {
    fun makeProduct(id: String) = Product(id, "Product $id", "Type $id", Product.Price(BigDecimal(1000), "EUR"), emptyMap(), null)
    fun makeCartProduct(product: Product, quantity: Int = 1) = Cart.CartProduct(product.id, product.price.currency, product.price.value, quantity)
}