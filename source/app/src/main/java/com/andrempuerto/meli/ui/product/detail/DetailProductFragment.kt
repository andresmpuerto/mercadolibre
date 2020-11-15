package com.andrempuerto.meli.ui.product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.andrempuerto.meli.databinding.FragmentDetailProductBinding
import com.andrempuerto.meli.ui.BaseFragment
import com.andrempuerto.meli.ui.product.ProductViewModel

class DetailProductFragment : BaseFragment<FragmentDetailProductBinding>() {

    private val viewModel by viewModels<ProductViewModel>()

    override fun getViewDataBinding(inflater: LayoutInflater):
            FragmentDetailProductBinding = FragmentDetailProductBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.product = viewModel.product
    }

}