package com.akinci.recipelist.features.acommon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akinci.recipelist.common.room.RoomConfig.Companion.CONTENT_ID
import com.akinci.recipelist.common.room.RoomConfig.Companion.ID
import com.akinci.recipelist.common.room.RoomConfig.Companion.RECIPE_TABLE_NAME
import com.akinci.recipelist.common.room.RoomConfig.Companion.TITLE

@Dao
interface RecipeDAO {

    /**
     * @Query, @Insert, @Update, @Delete
     *
     * @Delete annotation works using primary key of entity
     *
     * **/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<RecipeEntity>)

    @Query("DELETE FROM $RECIPE_TABLE_NAME")
    suspend fun deleteAll()

    @Query("SELECT * FROM $RECIPE_TABLE_NAME WHERE $CONTENT_ID =:contentId")
    suspend fun getRecipe(contentId: String) : RecipeEntity

    @Query("SELECT * FROM $RECIPE_TABLE_NAME WHERE $TITLE =:title")
    suspend fun getRecipeWithTitle(title: String) : RecipeEntity

    @Query("SELECT * FROM $RECIPE_TABLE_NAME ORDER BY $ID DESC")
    fun getAllRecipes() : List<RecipeEntity>
}