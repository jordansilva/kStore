package com.jordansilva.kstore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import com.jordansilva.kstore.R
import com.jordansilva.kstore.databinding.FragmentHomeBinding
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
    private val listAdapter: HomeProductListAdapter = HomeProductListAdapter { position, view -> onProductClicked(position, view) }

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
    }

    //TODO: Move to Navigator Component or move this to the activity
    private fun onProductClicked(item: ProductViewData, view: View) {
        parentFragmentManager.commit {
//            val imageView = view.findViewById<View>(R.id.image)
            setReorderingAllowed(true)
            val transitionName = view.transitionName
            val fragment = ProductDetailFragment.newInstance(item, transitionName)
            fragment.sharedElementEnterTransition = getTransition()
            fragment.sharedElementReturnTransition = getTransition()
            addSharedElement(view, transitionName)
            replace(R.id.container, fragment, ProductDetailFragment.TAG)
            addToBackStack(null)
        }
    }

    private fun getTransition(): TransitionSet {
        return TransitionSet().apply {
            ordering = TransitionSet.ORDERING_TOGETHER
            addTransition(ChangeBounds())
            addTransition(ChangeImageTransform())
            addTransition(ChangeTransform())
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}