package com.jordansilva.kstore.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jordansilva.kstore.R
import com.jordansilva.kstore.databinding.FragmentProductDetailBinding
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

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { product = it.getParcelable(ARG_ITEM) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.product.observe(viewLifecycleOwner, { handleViewState(it) })
        loadProductDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleViewState(viewState: ProductDetailViewState) {
        when (viewState) {
            ProductDetailViewState.NoProductFound -> Toast.makeText(requireContext(), R.string.product_not_found, Toast.LENGTH_LONG).show()
            is ProductDetailViewState.ProductDetail -> {
                product = viewState.data
                loadProductDetails()
            }
        }
    }

    private fun loadProductDetails() {
        binding.name.text = product?.name
        binding.info.text = product?.type
        product?.image?.let { url ->
            Glide.with(this)
                .load(url)
                .into(binding.image)
        }
    }
}