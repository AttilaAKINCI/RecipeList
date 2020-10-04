package com.akinci.recipelist.features.recipes.recipeListing.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.akinci.recipelist.commons.data.model.ListRowDataContract
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECIPE_CALORIES
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECIPE_CHEF_NAME
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECIPE_DESCRIPTION
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECIPE_IMG_URL
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECIPE_TABLE_NAME
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECIPE_TAG
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECIPE_TITLE
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.RECORD_ID
import com.akinci.recipelist.features.recipes.recipeListing.data.model.RecipeModel

@Entity(tableName = RECIPE_TABLE_NAME, indices = [Index(value = [RECIPE_TITLE], unique = true)])
data class RecipeEntity constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = RECORD_ID)
    var recordId : Long = 0L,

    @ColumnInfo(name = RECIPE_TITLE)
    var title : String = "",

    @ColumnInfo(name = RECIPE_IMG_URL)
    var imageUrl : String = "",

    @ColumnInfo(name = RECIPE_DESCRIPTION)
    var description : String = "",

    @ColumnInfo(name = RECIPE_CALORIES)
    var calories : Double = 0.0,

    @ColumnInfo(name = RECIPE_CHEF_NAME)
    var chefName : String = "",

    @ColumnInfo(name = RECIPE_TAG)
    var tags : String = "" ) : ListRowDataContract
{
    // empty constructor
    constructor() : this(recordId = 0)

    // for a recipe
    constructor(title: String, imageUrl: String, description: String, calories: Double, chefName: String, tags: String) : this(
        recordId = 0,
        title = title,
        imageUrl = imageUrl,
        description = description,
        calories = calories,
        chefName = chefName,
        tags = tags
    )
}

/** For integrity between network and ROOM Database **/
fun List<RecipeEntity>.asCompareModel() : List<RecipeModel> {
    var recipes = mutableListOf<RecipeModel>()

    apply {
        map {
            var recipeModel = RecipeModel(
                it.title,
                it.imageUrl,
                it.description,
                it.calories,
                it.chefName,
                it.tags.split(";")
            )
            recipes.add(recipeModel)
        }
    }
    return recipes.toList()
}