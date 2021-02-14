package com.jordansilva.kstore.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jordansilva.kstore.R

class ProductInfoListAdapter(private val data: List<Pair<String, String>>) : RecyclerView.Adapter<ProductInfoListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_information, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view.rootView) {
        private var name: TextView = view.findViewById(R.id.name)
        private var value: TextView = view.findViewById(R.id.value)

        fun bindView(item: Pair<String, String>) {
            name.text = item.first
            value.text = item.second
        }
    }


}