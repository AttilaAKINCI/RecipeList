package com.akinci.recipelist.features.recipes.recipeListing.data.local

class DBConstants {
    companion object{
        /** DB Related **/
        @JvmStatic val DB_NAME = "recipeDB"

        /** Entity Related **/
        const val RECIPE_TABLE_NAME = "recipetable"

        const val RECORD_ID = "recordId"
        const val RECIPE_TITLE = "recipeTitle"
        const val RECIPE_IMG_URL = "recipeImageUrl"
        const val RECIPE_DESCRIPTION = "recipeDescription"
        const val RECIPE_CALORIES = "recipeCalories"
        const val RECIPE_CHEF_NAME = "recipeChefName"
        const val RECIPE_TAG = "recipeTag"
    }
}