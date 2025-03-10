package com.basic.cooknest

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeDetailsFragment : Fragment() {

    private lateinit var recipeLayout: LinearLayout
    private lateinit var recipeName: TextView
    private lateinit var recipeRating: TextView
    private lateinit var recipeTime: TextView
    private lateinit var recipeType: TextView
    private lateinit var recipeDescription: TextView
    private lateinit var recipeIngredients: TextView
    private lateinit var recipeSteps: TextView
    private lateinit var likeBtn: ImageView

    private var isLiked = false
    private var recipe: Recipe? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)

        // Initialize Views
        recipeLayout = view.findViewById(R.id.recipe_detail_image)
        recipeName = view.findViewById(R.id.recipe_detail_name)
        recipeRating = view.findViewById(R.id.recipe_rating)
        recipeTime = view.findViewById(R.id.recipe_time)
        recipeType = view.findViewById(R.id.recipe_type)
        recipeDescription = view.findViewById(R.id.recipe_desc)
        recipeIngredients = view.findViewById(R.id.recipe_ind)
        recipeSteps = view.findViewById(R.id.recipe_steps)
        likeBtn = view.findViewById(R.id.likeBtn)

        // Get SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("wishlist_prefs", Context.MODE_PRIVATE)

        // Get Recipe Data from Arguments
        recipe = arguments?.getSerializable("recipe") as? Recipe
        recipe?.let { setRecipeDetails(it) }

        // Handle Like Button Click
        likeBtn.setOnClickListener {
            recipe?.let {
                isLiked = !isLiked
                it.liked = isLiked // Update the recipe object
                updateLikeButton()
                saveToWishlist(it)
                Log.d("LikeButton", "Clicked! isLiked = $isLiked")
            }
        }

        return view
    }

    private fun setRecipeDetails(recipe: Recipe) {
        recipeName.text = recipe.recipe_name
        recipeRating.text = recipe.rating.toString()
        recipeTime.text = recipe.time
        recipeType.text = recipe.type
        recipeDescription.text = recipe.description
        recipeIngredients.text = recipe.ingredients.joinToString("\n• ", "• ")
        recipeSteps.text = recipe.instructions.joinToString("\n\n")

        // Load Image as Background using Glide
        Glide.with(requireActivity())
            .load(recipe.image)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    recipeLayout.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    recipeLayout.background = placeholder
                }
            })

        // Check if the recipe is already liked
        isLiked = isRecipeLiked(recipe)
        recipe.liked = isLiked // Ensure recipe object updates with liked status
        updateLikeButton()
    }

    private fun updateLikeButton() {
        likeBtn.setImageResource(if (isLiked) R.drawable.heart_filled else R.drawable.heart)
    }

    private fun saveToWishlist(recipe: Recipe) {
        val gson = Gson()
        val wishlist = getWishlist().toMutableList()

        if (recipe.liked) {
            if (!wishlist.any { it.recipe_name == recipe.recipe_name }) {
                wishlist.add(recipe)
            }
        } else {
            wishlist.removeAll { it.recipe_name == recipe.recipe_name }
        }

        val json = gson.toJson(wishlist)
        sharedPreferences.edit().putString("wishlist", json).apply()
    }

    private fun getWishlist(): List<Recipe> {
        val gson = Gson()
        val json = sharedPreferences.getString("wishlist", "[]")
        val type = object : TypeToken<List<Recipe>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    private fun isRecipeLiked(recipe: Recipe): Boolean {
        return getWishlist().any { it.recipe_name == recipe.recipe_name }
    }
}
