package com.akinci.recipelist.features.recipes.recipeListing.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECIPE_TABLE_NAME
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECIPE_TITLE
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECORD_ID

@Dao
interface RecipeDAO {
    /**
     * @Query, @Insert, @Update, @Delete
     *
     * @Delete annotation works using primary key of entity
     *
     * **/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe : RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(plants: List<RecipeEntity>)

    @Query("DELETE FROM $RECIPE_TABLE_NAME")
    suspend fun deleteAll()

    @Query("SELECT * FROM $RECIPE_TABLE_NAME WHERE $RECORD_ID =:recordId")
    suspend fun getRecipe(recordId : Long) : RecipeEntity

    @Query("SELECT * FROM $RECIPE_TABLE_NAME WHERE $RECIPE_TITLE =:title")
    suspend fun getRecipe(title : String) : RecipeEntity

    @Query("SELECT * FROM $RECIPE_TABLE_NAME ORDER BY $RECORD_ID DESC")
    fun getAllRecipes() : LiveData<List<RecipeEntity>>
}