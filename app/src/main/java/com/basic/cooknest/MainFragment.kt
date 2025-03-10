package com.basic.cooknest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainFragment : Fragment() {

    private lateinit var trendingAdapter: TrendingAdapter
    private lateinit var otherRecipesAdapter: OtherRecipesAdapter
    private lateinit var trendingRecipes: List<Recipe>
    private lateinit var otherRecipes: List<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Load JSON data from assets
        val recipes = loadRecipesFromJson(requireContext())

        // Divide recipes into two categories
        trendingRecipes = recipes.take(5) // First 5 recipes as trending
        otherRecipes = recipes.drop(5)    // Rest as other recipes

        // Initialize adapters
        trendingAdapter = TrendingAdapter(
            trendingRecipes,
            requireActivity()
        )
        otherRecipesAdapter = OtherRecipesAdapter(otherRecipes,requireActivity())

        // Set up horizontal RecyclerView for trending recipes
        view.findViewById<RecyclerView>(R.id.trending_recipes_recycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }

        // Set up vertical RecyclerView for other recipes
        view.findViewById<RecyclerView>(R.id.other_recipes_recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = otherRecipesAdapter
        }

        return view
    }

    private fun loadRecipesFromJson(context: Context): List<Recipe> {
        return try {
            val jsonString = context.assets.open("recipes.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<Recipe>>() {}.type
            Gson().fromJson(jsonString, listType)
        } catch (e: IOException) {
            Log.e("MainFragment", "Error reading JSON", e)
            emptyList()
        }
    }
}
