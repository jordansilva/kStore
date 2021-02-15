package com.jordansilva.kstore.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jordansilva.kstore.databinding.ItemCartProductCardBinding
import com.jordansilva.kstore.ui.model.CartViewData.CartProductViewData

class CartListAdapter(
    private val onItemClicked: (CartProductViewData) -> Unit,
    private val onItemQuantityChanged: (CartProductViewData, Int) -> Unit
) :
    ListAdapter<CartProductViewData, CartListAdapter.ProductViewHolder>(photoViewDataDiff) {

    private companion object {
        val photoViewDataDiff = object : DiffUtil.ItemCallback<CartProductViewData>() {
            override fun areItemsTheSame(oldItem: CartProductViewData, newItem: CartProductViewData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CartProductViewData, newItem: CartProductViewData): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemCartProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(
            binding, { position -> if (position >= 0) onItemClicked(getItem(position)) },
            { position, quantity -> if (position >= 0) onItemQuantityChanged(getItem(position), quantity) },
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class ProductViewHolder(
        private val binding: ItemCartProductCardBinding,
        private val onItemClicked: (Int) -> Unit,
        private val onItemQuantityChanged: (Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.image.setOnClickListener { onItemClicked(adapterPosition) }
            binding.increaseQuantity.setOnClickListener { onItemQuantityChanged(adapterPosition, 1) }
            binding.reduceQuantity.setOnClickListener { onItemQuantityChanged(adapterPosition, -1) }
        }

        fun bindView(item: CartProductViewData) {
            binding.name.text = item.name
            binding.quantity.text = item.quantity.toString()
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