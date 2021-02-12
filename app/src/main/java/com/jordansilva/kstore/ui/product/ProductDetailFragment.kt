package com.jordansilva.kstore.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.kstore.R
import com.jordansilva.kstore.ui.ViewModelFactoryProducer
import com.jordansilva.kstore.ui.model.ProductViewData

/**
 * A fragment representing a list of Items.
 */
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    companion object {
        fun newInstance() = ProductDetailFragment()
    }

    private val viewModel by viewModels<ProductDetailViewModel>(factoryProducer = { ViewModelFactoryProducer(requireContext()) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}