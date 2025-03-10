package com.basic.cooknest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.basic.cooknest.TrendingAdapter.TrendingViewHolder
import com.bumptech.glide.Glide

class WishlistAdapter(private val wishlistRecipes: List<Recipe>) :
    RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {
       class WishlistViewHolder(view: View) : RecyclerView.ViewHolder(view){
           val image: ImageView = view.findViewById(R.id.wishlist_image)
           val title: TextView = view.findViewById(R.id.wishlist_title)
           val time: TextView = view.findViewById(R.id.wishlist_time)
           val rating: TextView = view.findViewById(R.id.wishlist_rating)
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val recipe = wishlistRecipes[position]
        Glide.with(holder.itemView.context)
            .load(recipe.image) // ✅ Load from URL
                     .into(holder.image)
        holder.title.text = recipe.recipe_name
        holder.time.text = recipe.time
        holder.rating.text = recipe.rating.toString()
    }

    override fun getItemCount(): Int = wishlistRecipes.size

}