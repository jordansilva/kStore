package com.jordansilva.kstore.data.mapper

import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.util.getStringOrNull
import org.json.JSONObject
import java.math.BigDecimal

object ProductMapper {

    fun fromJsonObject(source: JSONObject): Product {
        return Product(
            id = source.getString("id"),
            name = source.getString("name"),
            type = source.getString("type"),
            price = toPrice(source.getJSONObject("price")),
            image = source.getStringOrNull("imageUrl"),
            info = toInfo(source.getJSONObject("info"))
        )
    }

    private fun toInfo(source: JSONObject): Map<String, Any> {
        val hashMap = mutableMapOf<String, Any>()
        source.keys().forEach { hashMap[it] = source[it] }
        return hashMap
    }

    private fun toPrice(source: JSONObject): Product.Price = Product.Price(
        BigDecimal.valueOf(source.getLong("value")),
        source.getString("currency")
    )

}