package com.akinci.recipelist.features.acommon.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.akinci.recipelist.common.room.RoomConfig.Companion.CALORIES
import com.akinci.recipelist.common.room.RoomConfig.Companion.CHEF_NAME
import com.akinci.recipelist.common.room.RoomConfig.Companion.CONTENT_ID
import com.akinci.recipelist.common.room.RoomConfig.Companion.DESCRIPTION
import com.akinci.recipelist.common.room.RoomConfig.Companion.ID
import com.akinci.recipelist.common.room.RoomConfig.Companion.IMG_URL
import com.akinci.recipelist.common.room.RoomConfig.Companion.RECIPE_TABLE_NAME
import com.akinci.recipelist.common.room.RoomConfig.Companion.TAG
import com.akinci.recipelist.common.room.RoomConfig.Companion.TITLE
import com.akinci.recipelist.features.acommon.data.output.RecipeResponse

@Entity(tableName = RECIPE_TABLE_NAME, indices = [Index(value = [ID], unique = true)])
data class RecipeEntity constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id:Long = 0L,

    @ColumnInfo(name = CONTENT_ID)
    val contentId:String,

    @ColumnInfo(name = TITLE)
    val title:String,

    @ColumnInfo(name = IMG_URL)
    val imageUrl:String,

    @ColumnInfo(name = DESCRIPTION)
    val description:String,

    @ColumnInfo(name = CALORIES)
    val calories:Double,

    @ColumnInfo(name = CHEF_NAME)
    val chefName:String?,

    @ColumnInfo(name = TAG)
    val tags:String?
)

/** For integrity between network and ROOM Database **/
fun RecipeEntity.convertRecipeEntityToRecipeResponse() : RecipeResponse {
    return RecipeResponse(
                id,
                contentId,
                title,
                imageUrl,
                description,
                calories,
                chefName,
                tags?.split(";")
            )
}

fun List<RecipeEntity>.convertRecipeEntityToRecipeResponse() : List<RecipeResponse> {
    var recipes = mutableListOf<RecipeResponse>()

    map {
        recipes.add(it.convertRecipeEntityToRecipeResponse())
    }
    return recipes.toList()
}