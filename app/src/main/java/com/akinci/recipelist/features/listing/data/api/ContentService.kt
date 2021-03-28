package com.akinci.recipelist.features.listing.data.api

import com.akinci.recipelist.BuildConfig
import com.akinci.recipelist.common.helper.Resource
import com.akinci.recipelist.features.acommon.data.local.RecipeMapper
import com.akinci.recipelist.features.acommon.data.output.RecipeResponse
import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDAEntry
import timber.log.Timber
import javax.inject.Inject

class ContentService @Inject constructor(
    private val client : CDAClient,
    private val recipeMapper: RecipeMapper
) {

    fun fetchAllRecipes() =
        client.fetch(CDAEntry::class.java)
            .withContentType("recipe")
            .where("locale", BuildConfig.CDA_LOCALE)
            .all()
            .items()
            .map {
                recipeMapper.getRecipeResponseModel(it as CDAEntry)
            }

}