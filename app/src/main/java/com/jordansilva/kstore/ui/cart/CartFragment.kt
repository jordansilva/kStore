package com.jordansilva.kstore.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.jordansilva.kstore.R
import com.jordansilva.kstore.databinding.FragmentCartBinding
import com.jordansilva.kstore.ui.helper.navigateTo
import com.jordansilva.kstore.ui.model.CartViewData
import com.jordansilva.kstore.ui.product.ProductDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A fragment representing a list of Items.
 */
class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModel()
    private val listAdapter: CartListAdapter = CartListAdapter(this::onProductClicked, this::onProductQuantityChanged)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner) { updateViewState(it) }
        binding.recyclerView.adapter = listAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateViewState(viewState: CartViewState) {
        when (viewState) {
            is CartViewState.Updated -> updateCart(viewState.cart)
            is CartViewState.EmptyCart -> emptyCart()
        }
    }

    private fun updateCart(cart: CartViewData) {
        listAdapter.submitList(cart.products)
        binding.total.text = cart.totalPrice
        updateEmptyView()
    }

    private fun emptyCart() {
        listAdapter.submitList(emptyList())
        binding.total.text = ""
        updateEmptyView(true)
    }

    private fun updateEmptyView(isEmpty: Boolean = false) {
        if (!isEmpty && binding.viewSwitcher.currentView.id != binding.cartView.id) binding.viewSwitcher.showNext()
        if (isEmpty && binding.viewSwitcher.currentView.id == binding.cartView.id) binding.viewSwitcher.showNext()
    }

    private fun onProductClicked(item: CartViewData.CartProductViewData) {
        navigateTo(ProductDetailFragment.newInstance(item.id), ProductDetailFragment.TAG)
    }

    private fun onProductQuantityChanged(item: CartViewData.CartProductViewData, quantity: Int) {
        when (quantity) {
            1 -> viewModel.addProduct(item.id)
            -1 -> viewModel.removeProduct(item.id)
        }
    }


    companion object {
        val TAG: String = CartFragment::class.java.simpleName

        fun newInstance() = CartFragment()
    }

}