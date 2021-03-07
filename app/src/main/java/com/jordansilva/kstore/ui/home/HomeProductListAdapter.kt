package com.jordansilva.kstore.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jordansilva.kstore.databinding.ItemProductCardBinding
import com.jordansilva.kstore.ui.product.ProductViewData

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
        val binding = ItemProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding) { position -> onItemClicked(getItem(position)) }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class ProductViewHolder(private val binding: ItemProductCardBinding, private val onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { onItemClicked(adapterPosition) }
        }

        fun bindView(item: ProductViewData) {
            binding.name.text = item.name
            binding.type.text = item.type
            binding.price.text = item.price

            item.image?.let { url ->
                Glide.with(itemView)
                    .load(url)
                    .transform(CenterCrop(), RoundedCorners(25))
                    .into(binding.image)
            }
        }
    }
}