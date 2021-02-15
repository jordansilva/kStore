package com.jordansilva.kstore.helper

import com.jordansilva.kstore.domain.model.CartItem
import com.jordansilva.kstore.domain.model.Product
import java.math.BigDecimal

object TestUtil {
    fun makeProduct(id: String) = Product(id, "Product $id", "Type $id", Product.Price(BigDecimal(1000), "EUR"), emptyMap(), null)
    fun makeCartProduct(product: Product, quantity: Int = 1) = CartItem(product.id, product.name, product.image, quantity, product.price.currency, product.price.value)
}