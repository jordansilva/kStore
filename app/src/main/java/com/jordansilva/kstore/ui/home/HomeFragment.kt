package com.jordansilva.kstore.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.kstore.R
import com.jordansilva.kstore.ui.home.dummy.DummyContent
import com.jordansilva.kstore.ui.model.ProductViewData

/**
 * A fragment representing a list of Items.
 */
class HomeFragment : Fragment(R.layout.fragment_item_list) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val itemAdapter: ItemAdapter = ItemAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initList()
        updateList(DummyContent.ITEMS)
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