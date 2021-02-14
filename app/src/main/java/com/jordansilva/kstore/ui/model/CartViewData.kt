package com.jordansilva.kstore.ui.model

import com.jordansilva.kstore.domain.model.Cart
import java.math.BigDecimal

data class CartViewData(
    val products: List<CartProductViewData>,
    val quantityItems: Int,
    val totalPrice: BigDecimal
) {
    data class CartProductViewData(
        val id: String,
        val name: String,
        var quantity: Int,
        val price: BigDecimal,
        val currency: String,
        val image: String? = null
    ) {
        companion object {
            fun fromCartProduct(source: Cart.CartProduct) =
                CartProductViewData(source.id, source.id, source.quantity, source.price, source.currency)
        }
    }

    companion object {
        fun fromCart(source: Cart): CartViewData {
            return CartViewData(
                products = source.products.map(CartProductViewData::fromCartProduct),
                totalPrice = source.totalPrice(),
                quantityItems = source.quantityItems()
            )
        }
    }
}