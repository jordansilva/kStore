package com.jordansilva.kstore.data.remote

import com.jordansilva.kstore.data.repository.ProductsRemoteDataSource
import org.json.JSONArray
import org.json.JSONObject

class ProductsRemoteDataSourceImpl(private val rawDataSource: RawDataSource): ProductsRemoteDataSource {

    private companion object {
        const val KEY_PRODUCTS = "products"
    }

    override fun fetchProducts(): JSONArray {
        val content = rawDataSource.loadData()
        return JSONObject(content).getJSONArray(KEY_PRODUCTS)
    }

}

