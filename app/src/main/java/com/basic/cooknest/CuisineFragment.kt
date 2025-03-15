package com.basic.cooknest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CuisineFragment : Fragment() {

    private lateinit var cuisineAdapter: CuisineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cuisine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.cuisineRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val cuisineList = listOf(
            Cuisine("Indian", R.drawable.indian),
            Cuisine("Mexican", R.drawable.mexican),
            Cuisine("Lebanese", R.drawable.lebanese),
            Cuisine("Italian", R.drawable.italian),
            Cuisine("French", R.drawable.french),
            Cuisine("Chinese", R.drawable.chinese)
        )

        cuisineAdapter = CuisineAdapter(cuisineList) { selectedCuisine ->
            openRecipeListFragment(selectedCuisine)
        }

        recyclerView.adapter = cuisineAdapter
    }

    private fun openRecipeListFragment(cuisineName: String) {
        val recipeListFragment = RecipeListFragment().apply {
            arguments = Bundle().apply {
                putString("cuisine_name", cuisineName)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentloader, recipeListFragment) // Replace with RecipeListFragment
            .addToBackStack(null) // Add to back stack to allow back navigation
            .commit()
    }
}
