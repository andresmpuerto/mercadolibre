package com.andrempuerto.meli.ui.recentsearch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrempuerto.meli.databinding.FragmentRecentListBinding
import com.andrempuerto.meli.databinding.ListItemRecentBinding
import com.andrempuerto.meli.ui.product.search.ProductsAdapter

class RecentViewHolder(
    private val binding: ListItemRecentBinding,
    private val viewModel: RecentSearchViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(position: Int) {
        binding.apply {
            onClickListener = View.OnClickListener {
                viewModel.onItemClick(position)
            }
            recentQuery = viewModel.getItemAt(position)
        }
    }

}