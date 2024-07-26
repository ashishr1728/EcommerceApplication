package com.example.ecommerceapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(
    private val listOfProducts : List<product>,
    private val context : Context
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    class ProductViewHolder(
        view : View
    ):RecyclerView.ViewHolder(view){
        val productImg : ImageView = view.findViewById(R.id.product_image)
        val productName : TextView = view.findViewById(R.id.product_name)
        val productPrice : TextView = view.findViewById(R.id.product_price)
        val productDes : TextView = view.findViewById(R.id.product_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.product_layout, parent,false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentPos = listOfProducts[position]
        holder.productName.text = currentPos.productName
        holder.productPrice.text = currentPos.productPrice
        holder.productDes.text = currentPos.productDes
        Glide.with(context).load(currentPos.productImage).into(holder.productImg)
    }
}