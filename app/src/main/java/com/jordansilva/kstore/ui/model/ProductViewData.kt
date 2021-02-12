package com.jordansilva.kstore.ui.model

import com.jordansilva.kstore.domain.model.Product

data class ProductViewData(val id: String, val name: String, val type: String) {
    companion object {
        fun fromProduct(source: Product): ProductViewData {
            return ProductViewData(source.id, source.name, source.type)
        }
    }

    override fun toString(): String = name
}