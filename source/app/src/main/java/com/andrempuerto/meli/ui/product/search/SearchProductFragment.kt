package com.andrempuerto.meli.ui.product.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.FragmentDetailProductBinding
import com.andrempuerto.meli.databinding.FragmentHomeBinding
import com.andrempuerto.meli.databinding.FragmentSearchProductBinding
import com.andrempuerto.meli.ui.BaseFragment
import com.andrempuerto.meli.ui.product.ProductViewModel
import com.google.android.material.appbar.AppBarLayout

class SearchProductFragment : BaseFragment<FragmentSearchProductBinding>(),
    SearchView.OnQueryTextListener{

    private lateinit var productViewModel: ProductViewModel

    override fun getViewDataBinding(inflater: LayoutInflater) =
        FragmentSearchProductBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.svProduct.apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            setOnQueryTextListener(this@SearchProductFragment)
        }

        binding.rvProducts.setHasFixedSize(true)

        productViewModel.getProductsByQuery("Hola").observe(viewLifecycleOwner){
            productViewModel.setItems(it)
        }

        productViewModel.product.observe(viewLifecycleOwner) {
            //TODO go to Detail
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //TODO("Not yet implemented")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //TODO("Not yet implemented")
        return true
    }


}