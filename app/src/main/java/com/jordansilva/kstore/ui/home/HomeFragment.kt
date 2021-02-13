package com.jordansilva.kstore.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.kstore.R
import com.jordansilva.kstore.ui.model.ProductViewData
import com.jordansilva.kstore.ui.product.ProductDetailFragment
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A fragment representing a list of Items.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModel()
    private val listAdapter: HomeProductListAdapter = HomeProductListAdapter { onProductClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.products.observe(viewLifecycleOwner, { updateList(it) })
        initUi(view)
    }

    private fun initUi(view: View) {
        val recyclerView: RecyclerView = view as RecyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        recyclerView.adapter = listAdapter
    }

    private fun updateList(items: List<ProductViewData>) {
        listAdapter.submitList(items)
    }

    //TODO: Move to Navigator Component or move this to the activity
    private fun onProductClicked(item: ProductViewData) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, ProductDetailFragment.newInstance(item), ProductDetailFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

}