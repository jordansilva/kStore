package com.jordansilva.kstore.ui.model

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.model.CartItem
import com.jordansilva.kstore.util.MoneyHelper

data class CartViewData(
    val products: List<CartProductViewData>,
    val quantityItems: Int,
    val totalPrice: String
) {
    data class CartProductViewData(
        val id: String,
        val name: String,
        var quantity: Int,
        val price: String,
        val image: String? = null
    )

    companion object {
        fun fromCart(source: Cart): CartViewData {
            val totalPrice = MoneyHelper.formatPrice(source.products.first().currency, source.totalPrice())
            return CartViewData(
                products = source.products.map(::fromCartProduct),
                totalPrice = totalPrice,
                quantityItems = source.quantityItems()
            )
        }

        private fun fromCartProduct(source: CartItem): CartProductViewData {
            val totalPrice = MoneyHelper.formatPrice(source.currency, source.totalPrice())
            return CartProductViewData(
                id = source.id,
                name = source.name,
                quantity = source.quantity,
                price = totalPrice,
                image = source.image
            )
        }
    }
}