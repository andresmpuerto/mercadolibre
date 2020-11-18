package com.andrempuerto.meli.ui.product.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.FragmentSearchProductBinding
import com.andrempuerto.meli.model.Product
import com.andrempuerto.meli.ui.BaseFragment
import com.andrempuerto.meli.ui.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchProductFragment : BaseFragment<FragmentSearchProductBinding>(),
    SearchView.OnQueryTextListener {

    private val productViewModel by viewModels<ProductViewModel>()

    private lateinit var adapter: ProductsAdapter

    override fun getViewDataBinding(inflater: LayoutInflater) =
        FragmentSearchProductBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        setHasOptionsMenu(true)
        adapter = ProductsAdapter(productViewModel)
        with(binding) {
            adapterList = adapter
            svProduct.apply {
                setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
                setOnQueryTextListener(this@SearchProductFragment)
            }
        }

        productViewModel.products.observe(viewLifecycleOwner) {
            setItems(it)
        }

        productViewModel.itemSelected.observe(viewLifecycleOwner) {
            adapter.getItemByIndex(it)?.let { product ->
                val action = SearchProductFragmentDirections.actionToDetail(product)
                findNavController().navigate(action)
            }
        }

        binding.retryButton.setOnClickListener {
            adapter.retry()
        }

        statePagingAdapter()
    }

    private fun statePagingAdapter() {
        adapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    binding.rvProducts.isVisible = true
                    binding.textError.isVisible = false
                    binding.progressBar.isVisible = false
                    binding.retryButton.isVisible = false
                }

                is LoadState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.rvProducts.isVisible = false
                    binding.textError.isVisible = false
                    binding.retryButton.isVisible = false
                }

                is LoadState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.rvProducts.isVisible = false
                    binding.textError.isVisible = true
                    binding.retryButton.isVisible = true
                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                    errorState?.let {
                        binding.textError.text = it.error.message
                    }
                }
            }
        }
    }

    private fun setItems(items: PagingData<Product>) =
        lifecycleScope.launch(Dispatchers.IO) {
            adapter.submitData(items)
        }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { q ->
            hideKeyboard()
            productViewModel.setQuery(q)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
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
