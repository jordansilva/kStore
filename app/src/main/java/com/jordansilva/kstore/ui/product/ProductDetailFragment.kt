package com.jordansilva.kstore.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.jordansilva.kstore.R
import com.jordansilva.kstore.databinding.FragmentProductDetailBinding
import com.jordansilva.kstore.ui.model.ProductViewData
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A fragment representing a list of Items.
 */
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var product: ProductViewData

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        arguments?.let {
            product = it.getParcelable(ARG_ITEM)!!
            binding.image.transitionName = it.getString(ARG_TRANSITION_NAME)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.product.observe(viewLifecycleOwner, { handleViewState(it) })
        viewModel.getProductById(product.id)

        loadProductDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleViewState(viewState: ProductDetailViewState) {
        when (viewState) {
            is ProductDetailViewState.ProductDetail -> {
                product = viewState.data
                loadProductDetails()
            }
            ProductDetailViewState.NotFound -> showToast(R.string.product_not_found)
            ProductDetailViewState.AddedToCart -> showToast(R.string.product_added_to_cart)
            ProductDetailViewState.NotAddedToCart -> showToast(R.string.error_oops_try_again)
        }
    }

    private fun loadProductDetails() {
        binding.apply {
            name.text = product.name
            type.text = product.type
            price.text = product.price
            addToBasket.setOnClickListener { viewModel.addProductToBasket(product.id) }
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            recyclerView.adapter = ProductInfoListAdapter(product.info)
            product.image?.let { url ->
                Glide.with(requireContext())
                    .load(url)
                    .fitCenter()
                    .into(image)
            }
        }
    }

    private fun showToast(message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        val TAG: String = ProductDetailFragment::class.java.simpleName

        private const val ARG_ITEM = "product_item"
        private const val ARG_TRANSITION_NAME = "transition_name"

        fun newInstance(item: ProductViewData, transitionName: String) = ProductDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TRANSITION_NAME, transitionName)
                putParcelable(ARG_ITEM, item)
            }
        }
    }
}