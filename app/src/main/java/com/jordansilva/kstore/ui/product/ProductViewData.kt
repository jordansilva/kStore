package com.jordansilva.kstore.ui.product

import android.os.Parcelable
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.util.MoneyHelper
import kotlinx.parcelize.Parcelize
import java.text.NumberFormat

@Parcelize
data class ProductViewData(
    val id: String,
    val name: String,
    val type: String,
    val price: String,
    val image: String?,
    val info: List<Pair<String, String>> = emptyList()
) : Parcelable {

    override fun toString(): String = name

    companion object {
        private val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance()

        fun fromProduct(source: Product): ProductViewData {
            numberFormat.maximumFractionDigits = 0
            return ProductViewData(
                id = source.id,
                name = source.name,
                type = source.type,
                price = MoneyHelper.formatPrice(source.price.currency, source.price.value),
                image = source.image,
                info = source.info.toList()
            )
        }
    }
}