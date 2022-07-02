package com.example.technews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technews.R
import com.example.technews.model.Product

class ProductAdapter(var itemsList: MutableList<Product>):RecyclerView.Adapter<ProductViewHolder>() {

    fun setUpdatedData(itemsList: MutableList<Product>){

        this.itemsList = itemsList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_card,parent, false)
        return ProductViewHolder(view)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val prod =  itemsList[position]

        holder.productName.text = prod.name
        holder.productPrice.text = prod.price


        Glide.with(holder.productImage).load("http://192.168.157.1:3000/img/" + prod.image)
            .into(holder.productImage)

    }

    override fun getItemCount(): Int {

        return itemsList.size
    }

}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val productImage : ImageView = itemView.findViewById<ImageView>(R.id.product_image)
    val productName : TextView = itemView.findViewById<TextView>(R.id.product_name)
    val productPrice : TextView = itemView.findViewById<TextView>(R.id.product_price)
}