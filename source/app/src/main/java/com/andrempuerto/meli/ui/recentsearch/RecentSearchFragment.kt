package com.andrempuerto.meli.ui.recentsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.andrempuerto.meli.databinding.FragmentRecentListBinding
import com.andrempuerto.meli.ui.BaseFragment

class RecentSearchFragment : BaseFragment<FragmentRecentListBinding>() {

    private val recentSearchViewModel by viewModels<RecentSearchViewModel>()

    override fun getViewDataBinding(inflater: LayoutInflater):
            FragmentRecentListBinding = FragmentRecentListBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}