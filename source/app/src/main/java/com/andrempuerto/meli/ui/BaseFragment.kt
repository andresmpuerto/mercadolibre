package com.andrempuerto.meli.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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

    protected fun hideKeyboard() {
        activity?.let {
            it.currentFocus?.let { view ->
                val imm =
                    it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}