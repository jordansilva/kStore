package com.jordansilva.kstore.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jordansilva.kstore.databinding.ItemProductCardBinding
import com.jordansilva.kstore.ui.model.ProductViewData

class HomeProductListAdapter(private val onItemClicked: (ProductViewData, View) -> Unit) :
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
        return ProductViewHolder(binding) { position, view -> onItemClicked(getItem(position), view) }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class ProductViewHolder(private val binding: ItemProductCardBinding, private val onItemClicked: (Int, View) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
//            itemView.setOnClickListener { onItemClicked(adapterPosition, binding.image) }
        }

        fun bindView(item: ProductViewData) {
            binding.name.text = item.name
            binding.type.text = item.type
            binding.price.text = "0"
            ViewCompat.setTransitionName(binding.image, "transition_image_${adapterPosition}")
            itemView.setOnClickListener { onItemClicked(adapterPosition, binding.image) }

            item.image?.let { url ->
                Glide.with(itemView)
                    .load(url)
                    .transform(CenterCrop(), RoundedCorners(25))
                    .into(binding.image)
            }
        }
    }
}