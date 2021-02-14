package com.jordansilva.kstore.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jordansilva.kstore.R
import com.jordansilva.kstore.ui.helper.navigateTo
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A fragment representing a list of Items.
 */
@Suppress("unused")
class CartBadgeFragment : Fragment() {
    private val viewModel: CartViewModel by sharedViewModel()
    private var itemBadge: TextView? = null
    private val observer: Observer<in Int> = Observer { updateShoppingCart(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewModel.quantityItems.observeForever(observer)
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.quantityItems.removeObserver(observer)
        itemBadge = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.shopping_cart)
        menuItem.actionView?.setOnClickListener { openShoppingCart() }
        itemBadge = menuItem.actionView.findViewById(R.id.cart_badge)
        viewModel.quantityItems.value?.let { updateShoppingCart(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shopping_cart -> openShoppingCart()
        }

        return true
    }

    private fun openShoppingCart() {
        navigateTo(CartFragment.newInstance(), CartFragment.TAG)
    }

    private fun updateShoppingCart(quantity: Int) {
        itemBadge?.isVisible = quantity > 0
        itemBadge?.text = quantity.toString()
    }

    companion object {
        fun newInstance() = CartBadgeFragment()
    }

}