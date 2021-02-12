package com.jordansilva.kstore.ui.home

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
class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val itemAdapter: ItemAdapter = ItemAdapter()
    private val viewModel by viewModels<HomeViewModel>(factoryProducer = { ViewModelFactoryProducer(requireContext()) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.products.observe(viewLifecycleOwner, { updateList(it) })
        initList()
    }

    private fun initList() {
        val recyclerView: RecyclerView = view as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = itemAdapter
    }

    private fun updateList(items: List<ProductViewData>) {
        itemAdapter.submitList(items)
    }

}