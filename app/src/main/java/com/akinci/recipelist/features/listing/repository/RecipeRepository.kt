package com.akinci.recipelist.features.listing.repository

import com.akinci.recipelist.common.helper.Resource
import com.akinci.recipelist.common.network.NetworkChecker
import com.akinci.recipelist.common.repository.BaseRepositoryImpl
import com.akinci.recipelist.features.acommon.data.local.RecipeDAO
import com.akinci.recipelist.features.acommon.data.local.convertRecipeEntityToRecipeResponse
import com.akinci.recipelist.features.acommon.data.output.RecipeResponse
import com.akinci.recipelist.features.acommon.data.output.convertRecipeResponseToRecipeEntity
import com.akinci.recipelist.features.listing.data.api.ContentService
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val localDataSource: RecipeDAO,
    private val remoteDataSource: ContentService,
    networkChecker: NetworkChecker
) : BaseRepositoryImpl(networkChecker) {

    suspend fun fetchAllRecipes(): Resource<List<RecipeResponse>> {
        return callService(
            contentFulServiceAction = { remoteDataSource.fetchAllRecipes() },
            localServiceAction = { localDataSource.getAllRecipes().convertRecipeEntityToRecipeResponse() },
            localBackupAction = {
                localDataSource.insertAll(it.convertRecipeResponseToRecipeEntity())
            }
        )
    }
}