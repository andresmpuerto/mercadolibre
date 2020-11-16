package com.andrempuerto.meli.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.FragmentHomeBinding
import com.andrempuerto.meli.ui.BaseFragment
import com.andrempuerto.meli.utils.Logger
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun getViewDataBinding(inflater: LayoutInflater) =
        FragmentHomeBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onClickListener = this
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cv_col -> {
                homeViewModel.saveSelectSiteId("MCO")
                val action = HomeFragmentDirections.actionToListProducts()
                findNavController().navigate(action)
            }

            R.id.cv_ar -> {
                homeViewModel.saveSelectSiteId("MLA")
                val action = HomeFragmentDirections.actionToListProducts()
                findNavController().navigate(action)
            }
        }
    }
}
