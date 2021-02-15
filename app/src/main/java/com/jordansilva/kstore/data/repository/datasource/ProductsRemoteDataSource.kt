package com.jordansilva.kstore.data.repository.datasource

import org.json.JSONArray

interface ProductsRemoteDataSource {
    suspend fun fetchProducts(): JSONArray
}