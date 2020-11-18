package com.andrempuerto.meli.ui.product.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.ListItemProductBinding
import com.andrempuerto.meli.model.Product
import com.andrempuerto.meli.ui.product.ProductViewModel

class ProductsAdapter(
    private val viewModel: ProductViewModel
) : PagingDataAdapter<Product, ProductViewHolder>(DIFF_PRODUCT) {

    private lateinit var binding: ListItemProductBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_product,
            parent,
            false
        )
        return ProductViewHolder(viewModel, binding)
    }

    fun getItemByIndex(index: Int) = getItem(index)


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItemByIndex(position)?.let {
            holder.bind(it, position)
        }
    }

    companion object {
        private val DIFF_PRODUCT = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}
