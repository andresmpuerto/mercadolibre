package com.andrempuerto.meli.ui.product.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrempuerto.meli.databinding.ListItemProductBinding
import com.andrempuerto.meli.model.Product
import com.andrempuerto.meli.ui.product.ProductViewModel

class ProductViewHolder(
    private val viewModel: ProductViewModel,
    private val binding: ListItemProductBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(mProduct:Product, position: Int) {
        binding.apply {
            onClickListener = View.OnClickListener {
                viewModel.itemClick(position)
            }
            product = mProduct
        }
    }
}