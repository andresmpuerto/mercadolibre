package com.andrempuerto.meli.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.andrempuerto.meli.data.ProductsPagingSource
import com.andrempuerto.meli.data.source.network.Api
import com.andrempuerto.meli.model.Product
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val api: Api){

    fun getSearchProducts(query: String, siteId: String): LiveData<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { ProductsPagingSource(api, query, siteId) }
        ).liveData
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}
