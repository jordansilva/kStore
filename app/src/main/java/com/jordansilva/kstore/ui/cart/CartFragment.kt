package com.jordansilva.kstore.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jordansilva.kstore.R
import com.jordansilva.kstore.databinding.FragmentCartBinding
import com.jordansilva.kstore.ui.model.CartViewData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A fragment representing a list of Items.
 */
class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by sharedViewModel()
    private val listAdapter: CartListAdapter = CartListAdapter(this::onProductClicked, this::onProductQuantityChanged)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
//        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner) { updateViewState(it) }
        binding.recyclerView.adapter = listAdapter
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
            is CartViewState.Updated -> listAdapter.submitList(viewState.cart.products)
        }
    }

    //TODO: Move to Navigator Component or move this to the activity
    private fun onProductClicked(item: CartViewData.CartProductViewData, view: View) {
//        val transitionName = view.transitionName
//        val fragment = ProductDetailFragment.newInstance(item, transitionName)
//        fragment.sharedElementEnterTransition = FragmentTransitions.getTransition()
//        fragment.sharedElementReturnTransition = FragmentTransitions.getTransition()
//
//        val sharedElements = listOf(Pair(view, transitionName))
//        navigateTo(fragment, ProductDetailFragment.TAG, sharedElements)
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