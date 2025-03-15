package com.basic.cooknest

import java.io.Serializable

data class Recipe(
    val id: Int,
    var recipe_name: String,
    var image: String,
    var cuisine_type: String,
    val rating: Double,
    val time: String,
    var type: String,
    var description: String,
    var ingredients: List<String>,
    var instructions: List<String>,
    var liked: Boolean,
    val chefName: String
) : Serializable

