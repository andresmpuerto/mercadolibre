package com.andrempuerto.meli.ui.recentsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.ListItemProductBinding
import com.andrempuerto.meli.databinding.ListItemRecentBinding

import com.andrempuerto.meli.model.RecentQuery
import com.andrempuerto.meli.ui.product.ProductViewModel
import com.andrempuerto.meli.ui.product.search.ProductViewHolder

class RecentAdapter(
    private val viewModel: RecentSearchViewModel
): PagingDataAdapter<RecentQuery, RecentViewHolder>(DIFF_QUERIES) {

    private lateinit var binding: ListItemRecentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_recent,
            parent,
            false
        )
        return RecentViewHolder(binding, viewModel)
    }

    fun getItemByIndex(index: Int) = getItem(index)

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(position)
    }

    companion object {
        private val DIFF_QUERIES = object : DiffUtil.ItemCallback<RecentQuery>() {
            override fun areItemsTheSame(oldItem: RecentQuery, newItem: RecentQuery): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: RecentQuery, newItem: RecentQuery): Boolean =
                oldItem == newItem
        }
    }

}