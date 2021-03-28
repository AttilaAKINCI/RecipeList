package com.akinci.recipelist.common.room

class RoomConfig {
    companion object {
        /** DB Related **/
        const val DB_NAME = "recipeDB"

        /** Entity Related **/
        const val RECIPE_TABLE_NAME = "recipeTable"
        // Table column content
        const val ID = "id"
        const val CONTENT_ID = "contentId"
        const val TITLE = "title"
        const val IMG_URL = "imageUrl"
        const val DESCRIPTION = "description"
        const val CALORIES = "calories"
        const val CHEF_NAME = "chefName"
        const val TAG = "tag"
    }
}