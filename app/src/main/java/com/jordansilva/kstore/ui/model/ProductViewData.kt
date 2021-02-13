package com.jordansilva.kstore.ui.model

import android.os.Parcelable
import com.jordansilva.kstore.domain.model.Product
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductViewData(val id: String, val name: String, val type: String, val image: String?) : Parcelable {
    companion object {
        fun fromProduct(source: Product): ProductViewData {
            return ProductViewData(source.id, source.name, source.type, source.image)
        }
    }

    override fun toString(): String = name
}