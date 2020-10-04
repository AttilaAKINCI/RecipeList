package com.akinci.recipelist.features.recipes.recipeListing.data.api

import com.akinci.recipelist.BuildConfig
import com.akinci.recipelist.commons.data.coroutines.Result
import com.akinci.recipelist.features.recipes.recipeListing.data.model.RecipeModel
import com.akinci.recipelist.features.recipes.recipeListing.data.model.fromRestEntry
import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDAEntry
import java.lang.Exception

private const val DEFAULT_LOCALE = "en-US"

open class ContentHandler : ContentService {

    private var client : CDAClient

    init {
        val endPoint = "https://cdn.${BuildConfig.CDA_HOST}/"
        client = CDAClient.builder().apply {
            setSpace(BuildConfig.CDA_SPACE)
            setToken(BuildConfig.CDA_TOKEN)
            setEndpoint(endPoint)
        }.build()
    }

    override fun fetchAllRecipes() : Result<List<RecipeModel>> {
        return try {
                val recipes = client
                                .fetch(CDAEntry::class.java)
                                .withContentType("recipe")
                                .where("locale", DEFAULT_LOCALE)
                                .all()
                                .items()
                                .map {
                                    RecipeModel.fromRestEntry(it as CDAEntry, DEFAULT_LOCALE)
                                }
                Result.success(recipes)
        }catch (e : Exception){
            Result.error(e.message.toString())
        }
    }

}