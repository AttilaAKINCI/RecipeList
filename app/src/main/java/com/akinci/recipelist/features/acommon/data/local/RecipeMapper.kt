package com.akinci.recipelist.features.acommon.data.local

import com.akinci.recipelist.BuildConfig
import com.akinci.recipelist.features.acommon.data.output.RecipeResponse
import com.contentful.java.cda.CDAAsset
import com.contentful.java.cda.CDAEntry
import com.contentful.java.cda.image.ImageOption

class RecipeMapper {

    private val locale = BuildConfig.CDA_LOCALE

    private fun getChefModel(entry: CDAEntry): String = entry.getField<String?>(locale, "name").orEmpty()
    private fun getTagModel(entry: CDAEntry): String =  entry.getField<String?>(locale, "name").orEmpty()

    fun getRecipeResponseModel(entry: CDAEntry) = RecipeResponse(
        0L,
        entry.getAttribute("id"),
        entry.getField<String?>(locale, "title").orEmpty().replace("\t"," "),
        try {
            entry.getField<CDAAsset?>(locale, "photo")?.urlForImageWith(
                ImageOption.https(),
                ImageOption.formatOf(ImageOption.Format.webp)
            ).orEmpty()
        } catch (_: Throwable) {""},
        entry.getField<String?>(locale, "description").orEmpty(),
        entry.getField(locale, "calories"),
        entry.getField<CDAEntry?>(locale, "chef")?.let {
            getChefModel(it)
        },
        entry.getField<List<CDAEntry>?>(locale, "tags")
            .orEmpty()
            .map { getTagModel(it) }
    )
}