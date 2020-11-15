package com.andrempuerto.meli.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.ListItemProductBinding
import com.andrempuerto.meli.model.Product
import com.andrempuerto.meli.ui.product.search.ProductViewHolder
import com.andrempuerto.meli.ui.product.search.ProductsAdapter
import dagger.Reusable
import kotlinx.coroutines.runBlocking

@Reusable
class ProductViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>().apply {
        listOf<Product>()
    }
    val products: LiveData<List<Product>> = _products

    private val _product = MutableLiveData<Product>().apply {
        Product(name = "")
    }
    val product: LiveData<Product> = _product

    val adapter: ProductsAdapter = ProductsAdapter(this)

    //---
    fun getProductsByQuery(query: String): LiveData<PagingData<Product>> {
        //TODO call repository
        return Pager(config = PagingConfig(pageSize = 50, enablePlaceholders = false),
            pagingSourceFactory = { GithubPagingSource("", query) }
        ).liveData
    }

    fun onItemClick(index: Int) {
        _product.value = getItemAt(index)
    }

    fun setItems(items: PagingData<Product>) =
        runBlocking {
            adapter.submitData(items)
        }

    fun getItemAt(index: Int) = adapter.getItemByIndex(index)
}

class GithubPagingSource(
    private val service: String,
    private val query: String
) : PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return LoadResult.Page(data = listOf(), prevKey = null, nextKey = null)
    }
}
