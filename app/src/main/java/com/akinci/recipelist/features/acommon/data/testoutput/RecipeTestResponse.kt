package com.akinci.recipelist.features.acommon.data.testoutput

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeTestResponse(
    val recipeList: List<Recipe>
)