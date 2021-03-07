package com.jordansilva.kstore.ui.product

sealed class ProductDetailViewState {
    data class ProductDetail(val data: ProductViewData) : ProductDetailViewState()
    object NotFound : ProductDetailViewState()
    object AddedToCart : ProductDetailViewState()
    object NotAddedToCart: ProductDetailViewState()
}