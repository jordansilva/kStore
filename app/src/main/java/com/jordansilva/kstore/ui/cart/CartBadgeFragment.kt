package com.jordansilva.kstore.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jordansilva.kstore.R
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A fragment representing a list of Items.
 */
@Suppress("unused")
class CartBadgeFragment : Fragment() {
    private val viewModel: CartViewModel by sharedViewModel()
    private var itemBadge: TextView? = null
    private val cartViewStateObserver: Observer<in CartViewState> = Observer { handleViewState(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewModel.viewState.observeForever(cartViewStateObserver)
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.viewState.removeObserver(cartViewStateObserver)
        itemBadge = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.shopping_cart)
        menuItem.actionView?.setOnClickListener { openShoppingCart() }
        itemBadge = menuItem.actionView.findViewById(R.id.cart_badge)
        viewModel.viewState.value?.let { handleViewState(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shopping_cart -> openShoppingCart()
        }

        return true
    }

    private fun handleViewState(viewState: CartViewState) {
        when (viewState) {
            is CartViewState.ItemsChanged -> updateShoppingCart(viewState.quantity)
        }
    }

    private fun openShoppingCart() {
        Toast.makeText(requireContext(), "Open Shopping Cart", Toast.LENGTH_SHORT).show()
    }

    private fun updateShoppingCart(quantity: Int) {
        itemBadge?.isVisible = quantity > 0
        itemBadge?.text = quantity.toString()
    }

    companion object {
        fun newInstance() = CartBadgeFragment()
    }

}