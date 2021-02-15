package com.jordansilva.kstore.data.repository

import com.jordansilva.kstore.data.mapper.ProductMapper
import com.jordansilva.kstore.data.repository.datasource.ProductsRemoteDataSource
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository
import com.jordansilva.kstore.util.map

class ProductsRepositoryImpl(
    private val localDataSource: ProductsLocalDataSource,
    private val remoteDataSource: ProductsRemoteDataSource
) : ProductsRepository {

    override suspend fun listAllProducts(): List<Product> {
        val result = remoteDataSource.fetchProducts()
        return result.map(ProductMapper::fromJsonObject).onEach(localDataSource::saveProduct)
    }

    override fun getProduct(id: String): Product? {
        return localDataSource.getProductById(id)
    }
}

