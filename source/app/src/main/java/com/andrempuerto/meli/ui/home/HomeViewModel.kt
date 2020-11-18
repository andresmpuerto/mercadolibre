package com.andrempuerto.meli.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrempuerto.meli.repository.CountrySiteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel
@ViewModelInject constructor(private val countrySiteRepository: CountrySiteRepository) : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text
    
    fun saveSelectSiteId(siteId: String){
        viewModelScope.launch {
            countrySiteRepository.updateSiteId(siteId)
            _text.value = "Ok"
        }
    }
}