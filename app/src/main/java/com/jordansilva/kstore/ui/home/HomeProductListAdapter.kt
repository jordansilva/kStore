package com.jordansilva.kstore.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.kstore.R
import com.jordansilva.kstore.ui.model.ProductViewData

class HomeProductListAdapter(private val onItemClicked: (ProductViewData) -> Unit) :
    ListAdapter<ProductViewData, HomeProductListAdapter.ProductViewHolder>(photoViewDataDiff) {

    private companion object {
        val photoViewDataDiff = object : DiffUtil.ItemCallback<ProductViewData>() {
            override fun areItemsTheSame(oldItem: ProductViewData, newItem: ProductViewData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ProductViewData, newItem: ProductViewData): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return ProductViewHolder(view) { position -> onItemClicked(getItem(position)) }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class ProductViewHolder(view: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view.rootView) {
        private var content: TextView = view.findViewById(R.id.item_text)

        init {
            itemView.setOnClickListener { onItemClicked(adapterPosition) }
        }

        fun bindView(item: ProductViewData) {
            content.text = item.name
        }
    }
}