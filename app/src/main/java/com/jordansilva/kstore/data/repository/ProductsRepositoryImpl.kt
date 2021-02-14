package com.jordansilva.kstore.data.repository

import com.jordansilva.kstore.data.mapper.ProductMapper
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository
import com.jordansilva.kstore.util.map
import org.json.JSONArray
import java.math.BigDecimal

class ProductsRepositoryImpl(private val remoteDataSource: ProductsRemoteDataSource) : ProductsRepository {

    //TODO: Stop parsing every time
    override fun listAllProducts(): List<Product> {
        val result = remoteDataSource.fetchProducts()
        return result.map(ProductMapper::fromJsonObject)
    }

    //TODO: Fix this
    override fun getProduct(id: String): Product? {
        return listAllProducts().firstOrNull { it.id == id }
    }
}

interface ProductsRemoteDataSource {
    fun fetchProducts(): JSONArray
}