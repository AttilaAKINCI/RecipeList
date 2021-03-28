package com.akinci.recipelist.features.acommon.data.output

import com.akinci.recipelist.features.acommon.data.local.RecipeEntity

data class RecipeResponse (
    var id:Long,
    var contentId:String,
    var title : String,
    var imageUrl : String,
    var description : String,
    var calories : Double,
    var chefName : String?,
    var tags : List<String>?
)

/** For integrity between network and ROOM Database **/
fun List<RecipeResponse>.convertRecipeResponseToRecipeEntity() : List<RecipeEntity> {
    var recipes = mutableListOf<RecipeEntity>()

    apply {
        map {
            var recipeEntity = RecipeEntity(
                id = it.id,
                contentId = it.contentId,
                title = it.title,
                imageUrl = it.imageUrl,
                description = it.description,
                calories = it.calories,
                chefName = it.chefName,
                tags = if(!it.tags.isNullOrEmpty()){
                    it.tags?.joinToString(separator = ";", prefix = "") { element -> "$element " }
                }else{
                    null
                }
            )
            recipes.add(recipeEntity)
        }
    }
    return recipes.toList()
}
