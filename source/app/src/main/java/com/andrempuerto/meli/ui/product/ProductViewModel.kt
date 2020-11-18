package com.andrempuerto.meli.ui.product

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andrempuerto.meli.model.Product
import com.andrempuerto.meli.repository.CountrySiteRepository
import com.andrempuerto.meli.repository.ProductsRepository
import com.andrempuerto.meli.utils.DefaultDispatcherProvider
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductViewModel
@ViewModelInject
constructor(
    private val repository: ProductsRepository,
    private val countrySiteRepository: CountrySiteRepository
) : ViewModel() {

    val _query = MutableLiveData<String>()

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    val itemSelected = LiveEvent<Int>()

    val products: LiveData<PagingData<Product>> = Transformations.switchMap(_query) {
            repository.getSearchProducts(it, siteId).cachedIn(viewModelScope)
    }

    var siteId = ""

    init {
        val dispatcher = DefaultDispatcherProvider()
        viewModelScope.launch(dispatcher.io()) {
            countrySiteRepository.siteIdFlow.collect {
                siteId = it.siteId
            }
        }
    }

    fun setQuery(query: String) {
        _query.value = query
    }

    fun itemClick(index: Int) {
        itemSelected.value = index
    }
}
