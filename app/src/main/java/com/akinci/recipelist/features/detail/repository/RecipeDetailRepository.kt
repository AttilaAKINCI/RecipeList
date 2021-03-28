package com.akinci.recipelist.features.detail.repository

import com.akinci.recipelist.common.network.NetworkChecker
import com.akinci.recipelist.common.repository.BaseRepositoryImpl
import com.akinci.recipelist.features.acommon.data.local.RecipeDAO
import com.akinci.recipelist.features.acommon.data.local.convertRecipeEntityToRecipeResponse
import javax.inject.Inject

class RecipeDetailRepository @Inject constructor(
    private val localService: RecipeDAO,
    networkChecker: NetworkChecker
) : BaseRepositoryImpl(networkChecker) {

    suspend fun fetchRecipe(contentId: String) =
        localService.getRecipe(contentId).convertRecipeEntityToRecipeResponse()

}