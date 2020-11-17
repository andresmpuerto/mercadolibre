package com.andrempuerto.meli.ui.product

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andrempuerto.meli.model.Product
import com.andrempuerto.meli.repository.CountrySiteRepository
import com.andrempuerto.meli.repository.ProductsRepository
import com.andrempuerto.meli.ui.product.search.ProductsAdapter
import com.andrempuerto.meli.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class ProductViewModel
@ViewModelInject
constructor(
    private val repository: ProductsRepository,
    private val countrySiteRepository: CountrySiteRepository
) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    val adapter: ProductsAdapter = ProductsAdapter(this)

    private val _products = MutableLiveData<PagingData<Product>>()
    val products: LiveData<PagingData<Product>> = _products

    private var siteId = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            countrySiteRepository.siteIdFlow.collect {
                siteId = it.siteId
            }
        }
    }

    fun getProductsByQuery(query: String): LiveData<PagingData<Product>> {
       return repository.getSearchProducts(query, siteId).cachedIn(viewModelScope)
    }

    fun onItemClick(index: Int) {
        _product.value = getItemAt(index)
    }

    fun setItems(items: PagingData<Product>) =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.submitData(items)
        }

    fun getItemAt(index: Int) = adapter.getItemByIndex(index)
}
