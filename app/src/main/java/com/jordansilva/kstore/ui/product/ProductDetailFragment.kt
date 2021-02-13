package com.jordansilva.kstore.ui.product

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jordansilva.kstore.R
import com.jordansilva.kstore.ui.model.ProductViewData
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A fragment representing a list of Items.
 */
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    companion object {
        val TAG: String = ProductDetailFragment::class.java.simpleName

        private const val ARG_ITEM = "product_item"

        fun newInstance(item: ProductViewData) = ProductDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_ITEM, item)
            }
        }
    }

    private val viewModel: ProductDetailViewModel by viewModel()
    private var product: ProductViewData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { product = it.getParcelable(ARG_ITEM) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI(view)
    }

    private fun initUI(view: View) {
        val txtName = view.findViewById<TextView>(R.id.name)
        txtName.text = product?.name
    }

}