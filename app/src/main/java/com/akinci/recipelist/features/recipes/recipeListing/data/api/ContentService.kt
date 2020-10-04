package com.akinci.recipelist.features.recipes.recipeListing.data.api

import com.akinci.recipelist.commons.data.coroutines.Result
import com.akinci.recipelist.features.recipes.recipeListing.data.model.RecipeModel

interface ContentService {
    fun fetchAllRecipes() : Result<List<RecipeModel>>
}