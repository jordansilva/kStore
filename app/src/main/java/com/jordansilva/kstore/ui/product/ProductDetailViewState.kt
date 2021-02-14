package com.jordansilva.kstore.ui.product

import com.jordansilva.kstore.ui.model.ProductViewData

sealed class ProductDetailViewState {
    data class ProductDetail(val data: ProductViewData) : ProductDetailViewState()
    object NotFound : ProductDetailViewState()
    object AddedToCart : ProductDetailViewState()
    object NotAddedToCart: ProductDetailViewState()
}