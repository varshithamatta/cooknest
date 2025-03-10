package com.basic.cooknest

import java.io.Serializable

data class Recipe(
    val id: Int,
    val recipe_name: String,
    val image: String,
    val cuisine_type: String,
    val rating: Double,
    val time: String,
    val type: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    var liked: Boolean
) : Serializable

