package com.basic.cooknest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.Serializable

class TrendingAdapter(private val trendingList: List<Recipe>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {

    class TrendingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.trending_image)
        val title: TextView = view.findViewById(R.id.trending_title)
        val time: TextView = view.findViewById(R.id.trending_time)
        val rating: TextView = view.findViewById(R.id.trending_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trending, parent, false)
        return TrendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val recipe = trendingList[position]

        Glide.with(holder.itemView.context)
            .load(recipe.image)
            .into(holder.image)

        holder.title.text = recipe.recipe_name
        holder.time.text = recipe.time
        holder.rating.text = recipe.rating.toString()

        // Click Listener to Open Fragment
        holder.itemView.setOnClickListener {
            val fragment = RecipeDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable("recipe", recipe)  // Passing the Recipe object
            fragment.arguments = bundle

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentloader, fragment)  // Replace with your container ID
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = trendingList.size
}
