package com.jordansilva.kstore.data.repository.datasource

import org.json.JSONArray

interface ProductsRemoteDataSource {
    fun fetchProducts(): JSONArray
}