package com.basic.cooknest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class RecipeListFragment : Fragment() {

    private lateinit var recipeAdapter: RecipesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        val cuisineTitle = view.findViewById<TextView>(R.id.cuisine_title)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recipes_list)

        val cuisineName = arguments?.getString("cuisine_name") ?: "Recipes"
        cuisineTitle.text = cuisineName

        val recipes = loadRecipesFromJson(requireContext()).filter { it.cuisine_type == cuisineName }

        recipeAdapter = RecipesListAdapter(recipes,requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recipeAdapter

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
