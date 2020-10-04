package com.akinci.recipelist.features.recipes.recipeListing.data.model

import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeEntity
import com.contentful.java.cda.CDAAsset
import com.contentful.java.cda.CDAEntry
import com.contentful.java.cda.image.ImageOption

data class RecipeModel (
    var title : String,
    var imageUrl : String,
    var description : String,
    var calories : Double,
    var chefName : String?,
    var tags : List<String>?
) {
    companion object
}

fun RecipeModel.Companion.chefRestEntry(entry: CDAEntry, locale: String): String = entry.getField<String?>(locale, "name").orEmpty()
fun RecipeModel.Companion.tagRestEntry(entry: CDAEntry, locale: String): String =  entry.getField<String?>(locale, "name").orEmpty()
fun RecipeModel.Companion.fromRestEntry(entry: CDAEntry, locale: String): RecipeModel = RecipeModel(
    entry.getField<String?>(locale, "title").orEmpty().replace("\t"," "),
    try {
        entry.getField<CDAAsset?>(locale, "photo")
            ?.urlForImageWith(ImageOption.https(), ImageOption.formatOf(ImageOption.Format.webp))
            .orEmpty()
    } catch (_: Throwable) {""},
    entry.getField<String?>(locale, "description").orEmpty(),
    entry.getField<Double?>(locale, "calories"),
    entry.getField<CDAEntry?>(locale, "chef")
        ?.let {
            chefRestEntry(it, locale)
        },
    entry.getField<List<CDAEntry>?>(locale, "tags")
        .orEmpty()
        .map { tagRestEntry(it, locale) }
)


/** For integrity between network and ROOM Database **/
fun RecipeModel.asDataBaseModel() : RecipeEntity {
    var recipeEntity : RecipeEntity
    apply {
        recipeEntity = RecipeEntity(
            this.title,
            this.imageUrl,
            this.description,
            this.calories,
            this.chefName ?: "",
            this.tags?.joinToString(";") ?: ""
        )
    }
    return recipeEntity
}