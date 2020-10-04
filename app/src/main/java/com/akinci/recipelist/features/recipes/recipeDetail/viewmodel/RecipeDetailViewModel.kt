package com.akinci.recipelist.features.recipes.recipeDetail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.akinci.recipelist.commons.data.coroutines.Result
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeDAO
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeDatabase
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeEntity
import kotlinx.coroutines.Dispatchers

class RecipeDetailViewModel(val app: Application, private val recipeId: Long) : AndroidViewModel(app) {

    private val recipeDAO: RecipeDAO = RecipeDatabase.getInstance(app).recipeDAO
    lateinit var recipeDetail: RecipeEntity

    fun fetchData () = liveData(Dispatchers.IO) {
        recipeDetail = recipeDAO.getRecipe(recipeId)
        emit(Result.success(recipeDetail))
    }

    /** Factory for constructing RecipeListViewModel with parameter **/
    class Factory(val context: Application, private val recipeId: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecipeDetailViewModel(context, recipeId) as T
            }
            throw IllegalArgumentException("recipeListViewModel construction failed")
        }
    }
}