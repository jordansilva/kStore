package com.jordansilva.kstore.ui.model

import android.os.Parcelable
import com.jordansilva.kstore.domain.model.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductViewData(
    val id: String,
    val name: String,
    val type: String,
    val image: String?,
    val info: List<Pair<String, String>> = emptyList()
) : Parcelable {

    companion object {
        fun fromProduct(source: Product): ProductViewData {
            return ProductViewData(source.id, source.name, source.type, source.image, source.info.toList())
        }
    }

    override fun toString(): String = name
}