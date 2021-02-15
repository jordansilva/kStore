package com.jordansilva.kstore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jordansilva.kstore.R
import com.jordansilva.kstore.databinding.FragmentHomeBinding
import com.jordansilva.kstore.ui.helper.navigateTo
import com.jordansilva.kstore.ui.model.ProductViewData
import com.jordansilva.kstore.ui.product.ProductDetailFragment
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A fragment representing a list of Items.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()
    private val listAdapter: HomeProductListAdapter = HomeProductListAdapter { position -> onProductClicked(position) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.products.observe(viewLifecycleOwner, { updateList(it) })
        binding.recyclerView.adapter = listAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateList(items: List<ProductViewData>) {
        listAdapter.submitList(items)
        if (items.isNotEmpty() && binding.viewSwitcher.currentView.id != binding.recyclerView.id) binding.viewSwitcher.showNext()
        if (items.isEmpty() && binding.viewSwitcher.currentView.id == binding.recyclerView.id) binding.viewSwitcher.showNext()
    }

    private fun onProductClicked(item: ProductViewData) {
        navigateTo(ProductDetailFragment.newInstance(item.id), ProductDetailFragment.TAG)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}