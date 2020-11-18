package com.andrempuerto.meli.ui.recentsearch

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.FragmentRecentListBinding
import com.andrempuerto.meli.ui.BaseFragment
import com.andrempuerto.meli.utils.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentSearchFragment : BaseFragment<FragmentRecentListBinding>() {

    private val recentSearchViewModel by viewModels<RecentSearchViewModel>()

    override fun getViewDataBinding(inflater: LayoutInflater):
            FragmentRecentListBinding = FragmentRecentListBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            adapter = recentSearchViewModel.adapter
        }
    }
}
