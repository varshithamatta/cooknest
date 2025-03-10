package com.basic.cooknest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WishlistFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var wishlistAdapter: WishlistAdapter
    private var wishlistRecipes: MutableList<Recipe> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.liked_recipes)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Get SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("wishlist_prefs", Context.MODE_PRIVATE)

        // Load wishlist recipes
        wishlistRecipes = getWishlist().toMutableList()
        wishlistAdapter = WishlistAdapter(wishlistRecipes)
        recyclerView.adapter = wishlistAdapter

        return view
    }

    private fun getWishlist(): List<Recipe> {
        val gson = Gson()
        val json = sharedPreferences.getString("wishlist", "[]")
        val type = object : TypeToken<List<Recipe>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}
