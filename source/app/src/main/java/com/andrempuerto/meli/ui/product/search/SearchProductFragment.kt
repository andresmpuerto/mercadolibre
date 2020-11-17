package com.andrempuerto.meli.ui.product.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.FragmentSearchProductBinding
import com.andrempuerto.meli.ui.BaseFragment
import com.andrempuerto.meli.ui.product.ProductViewModel
import com.andrempuerto.meli.utils.Logger
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

        productViewModel.product.observe(viewLifecycleOwner) {
            val action = SearchProductFragmentDirections.actionToDetail(it)
            findNavController().navigate(action)
        }

        binding.retryButton.setOnClickListener {
            productViewModel.adapter.retry()
        }

        statePagingAdapter()
    }

    private fun statePagingAdapter() {
        productViewModel.adapter.addLoadStateListener { loadState ->
            when(loadState.source.refresh ){
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { q ->
            hideKeyboard()
            productViewModel.getProductsByQuery(q).observe(viewLifecycleOwner) {
                productViewModel.setItems(it)
            }
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

    override fun onDestroy() {
        super.onDestroy()
        Logger.e("OnDestroy")
    }
    override fun onDetach() {
        super.onDetach()
        Logger.e("onDetach")
    }
}
