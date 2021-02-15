package com.jordansilva.kstore.ui.cart

import android.annotation.SuppressLint
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
import com.jordansilva.kstore.databinding.ItemCartProductCardBinding
import com.jordansilva.kstore.ui.model.CartViewData.CartProductViewData

class CartListAdapter(
    private val onItemClicked: (CartProductViewData, View) -> Unit,
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
            binding, { position, view -> if (position >= 0) onItemClicked(getItem(position), view) },
            { position, quantity -> if (position >= 0) onItemQuantityChanged(getItem(position), quantity) },
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class ProductViewHolder(
        private val binding: ItemCartProductCardBinding,
        private val onItemClicked: (Int, View) -> Unit,
        private val onItemQuantityChanged: (Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { onItemClicked(adapterPosition, binding.image) }
            binding.increaseQuantity.setOnClickListener { onItemQuantityChanged(adapterPosition, 1) }
            binding.reduceQuantity.setOnClickListener { onItemQuantityChanged(adapterPosition, -1) }
        }

        @SuppressLint("SetTextI18n")
        fun bindView(item: CartProductViewData) {
            binding.name.text = item.name
//            binding.type.text = item.type
            binding.quantity.text = item.quantity.toString()
            binding.price.text = item.price

            ViewCompat.setTransitionName(binding.image, "transition_image_${adapterPosition}")

            item.image?.let { url ->
                Glide.with(itemView)
                    .load(url)
                    .transform(CenterCrop(), RoundedCorners(25))
                    .into(binding.image)
            }
        }
    }
}