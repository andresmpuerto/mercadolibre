package com.andrempuerto.meli.ui.recentsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.andrempuerto.meli.model.Product
import com.andrempuerto.meli.model.RecentQuery
import com.andrempuerto.meli.ui.product.GithubPagingSource
import kotlinx.coroutines.runBlocking

class RecentSearchViewModel : ViewModel() {

    private val _recent  = MutableLiveData<List<RecentQuery>>()
    val recent: LiveData<List<RecentQuery>> = _recent

    private val _item  = MutableLiveData<RecentQuery>()
    val item: LiveData<RecentQuery> = _item

    val adapter: RecentAdapter = RecentAdapter(this)

    //---
    fun getLastQueries() { //: LiveData<PagingData<RecentQuery>> {
        //TODO call repository

    }

    fun onItemClick(index: Int) {
        _item.value = getItemAt(index)
    }

    fun setItems(items: PagingData<RecentQuery>) =
        runBlocking {
            adapter.submitData(items)
        }

    fun getItemAt(index: Int) = adapter.getItemByIndex(index)

}
