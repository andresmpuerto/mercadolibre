package com.andrempuerto.meli.ui.product.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.FragmentDetailProductBinding
import com.andrempuerto.meli.ui.BaseFragment
import com.andrempuerto.meli.ui.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailProductFragment : BaseFragment<FragmentDetailProductBinding>(), View.OnClickListener {

    private val viewModel by viewModels<ProductViewModel>()

    val detail : DetailProductFragmentArgs by navArgs()

    override fun getViewDataBinding(inflater: LayoutInflater)
            = FragmentDetailProductBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.product = detail.product
        binding.onClickListener = this
        binding.executePendingBindings()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.urlMeli -> {
                val uri = Uri.parse(detail.product.permalink)
                Intent(Intent.ACTION_VIEW, uri).also {
                    startActivity(it)
                }
            }
        }
    }

}