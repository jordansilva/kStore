package com.jordansilva.kstore.data.remote

import com.jordansilva.kstore.data.repository.datasource.ProductsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class ProductsRemoteDataSourceImpl(private val rawDataSource: RawDataSource) : ProductsRemoteDataSource {

    private companion object {
        const val KEY_PRODUCTS = "products"
    }

    override suspend fun fetchProducts(): JSONArray =
        withContext(Dispatchers.IO) {
            val content = rawDataSource.loadData()
            return@withContext JSONObject(content).getJSONArray(KEY_PRODUCTS)
        }
}

