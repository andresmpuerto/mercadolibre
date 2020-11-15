package com.andrempuerto.meli.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("setAdapter")
fun bindAdapter(recyclerView: RecyclerView, adapter: PagingDataAdapter<*, *>) {
    recyclerView.apply {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(recyclerView.context)
        this.adapter = adapter
    }
}

@BindingAdapter("isVisible")
fun bindToggle(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

