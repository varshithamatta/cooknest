package com.basic.cooknest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipesListAdapter(private val recipes: List<Recipe>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder>() {

    class  RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.other_image)
        val title: TextView = view.findViewById(R.id.other_title)
        val time: TextView = view.findViewById(R.id.other_time)
        val rating: TextView = view.findViewById(R.id.other_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_other, parent, false)
        return  RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder:  RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        Glide.with(holder.itemView.context)
            .load(recipe.image)
            .into(holder.image)

        holder.title.text = recipe.recipe_name
        holder.time.text = recipe.time
        holder.rating.text = recipe.rating.toString()

        holder.itemView.setOnClickListener {
            val fragment = RecipeDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable("recipe", recipe)
            fragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentloader, fragment)
                .addToBackStack(null)
                .commit()

        }
    }

    override fun getItemCount(): Int = recipes.size
}
