package com.akinci.recipelist.features.recipes.recipeListing.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.akinci.recipelist.commons.data.coroutines.Result
import com.akinci.recipelist.commons.data.coroutines.Status
import com.akinci.recipelist.features.recipes.recipeListing.data.api.ContentService
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeDAO
import com.akinci.recipelist.features.recipes.recipeListing.data.model.asDataBaseModel
import kotlinx.coroutines.Dispatchers

class RecipeRepository(private val localDataSource: RecipeDAO, private val remoteDataSource: ContentService){

    val recipes = singleSourceOfTruthData(
        databaseQuery = { localDataSource.getAllRecipes() },
        contentfulNetworkCall = { remoteDataSource.fetchAllRecipes() },
        saveCallResult = { localDataSource.insertAll( it.map { recipeModel -> recipeModel.asDataBaseModel() }) })

    private fun <T, A> singleSourceOfTruthData(databaseQuery: () -> LiveData<T>,
                                               contentfulNetworkCall: suspend () -> Result<A>,
                                               saveCallResult: suspend (A) -> Unit): LiveData<Result<T>> =
        liveData(Dispatchers.IO) {
            emit(Result.loading<T>())
            val source = databaseQuery.invoke().map { Result.success(it) }

            kotlinx.coroutines.delay(1000) // add this delay on purpose so as to make shimmer views visible
            emitSource(source)

            val responseStatus = contentfulNetworkCall.invoke()
            if (responseStatus.status == Status.SUCCESS) {
                saveCallResult(responseStatus.data!!)
            } else if (responseStatus.status == Status.ERROR) {
                emit(Result.error<T>(responseStatus.message!!))
                emitSource(source)
            }
        }
}