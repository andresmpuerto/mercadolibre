package com.andrempuerto.meli.ui.product.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.FragmentSearchProductBinding
import com.andrempuerto.meli.ui.BaseFragment
import com.andrempuerto.meli.ui.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchProductFragment : BaseFragment<FragmentSearchProductBinding>(),
    SearchView.OnQueryTextListener {

    private val productViewModel by viewModels<ProductViewModel>()

    override fun getViewDataBinding(inflater: LayoutInflater) =
        FragmentSearchProductBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        setHasOptionsMenu(true)
        with(binding) {
            adapterList = productViewModel.adapter
            svProduct.apply {
                setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
                setOnQueryTextListener(this@SearchProductFragment)
            }
        }

//        productViewModel.getProductsByQuery("Hola").observe(viewLifecycleOwner) {
//            productViewModel.setItems(it)
//        }
//
//        productViewModel.product.observe(viewLifecycleOwner) {
//            val action = SearchProductFragmentDirections.actionToDetail()
//            findNavController().navigate(action)
//        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //TODO("Not yet implemented")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //TODO("Not yet implemented")
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_history -> {
                val action = SearchProductFragmentDirections.actionToRecent()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
