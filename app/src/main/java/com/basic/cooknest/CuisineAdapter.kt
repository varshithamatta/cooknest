package com.basic.cooknest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CuisineAdapter(
    private val cuisines: List<Cuisine>,
    private val onCuisineClick: (String) -> Unit // Callback for click event
) : RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder>() {

    class CuisineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cuisineImage: ImageView = view.findViewById(R.id.cuisineImage)
        val cuisineName: TextView = view.findViewById(R.id.cuisine_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cuisine_card_item, parent, false)
        return CuisineViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuisineViewHolder, position: Int) {
        val cuisine = cuisines[position]
        holder.cuisineImage.setImageResource(cuisine.image)
        holder.cuisineName.text = cuisine.name

        holder.itemView.setOnClickListener {
            onCuisineClick(cuisine.name) // Pass selected cuisine back to fragment
        }
    }

    override fun getItemCount(): Int = cuisines.size
}
