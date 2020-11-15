package com.andrempuerto.meli.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.andrempuerto.meli.utils.Logger

abstract class BaseFragment<V: ViewDataBinding>: Fragment() {

    abstract fun getViewDataBinding(inflater: LayoutInflater): V

    lateinit var binding : V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.setTag(this::class.java.simpleName)
        binding = getViewDataBinding(inflater)
        return binding.root
    }
}