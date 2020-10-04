package com.akinci.recipelist.features.recipes.recipeListing.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akinci.recipelist.commons.data.coroutines.Result
import com.akinci.recipelist.features.recipes.recipeListing.data.api.ContentHandler
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeDatabase
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeEntity
import com.akinci.recipelist.features.recipes.recipeListing.data.repository.RecipeRepository

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    var listContent: LiveData<Result<List<RecipeEntity>>>
    val repository : RecipeRepository

    init {
        repository = RecipeRepository(RecipeDatabase.getInstance(application).recipeDAO, ContentHandler())
        listContent = repository.recipes
    }

    /** Factory for constructing RecipeListViewModel with parameter **/
    class Factory(val context: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecipeListViewModel(context) as T
            }
            throw IllegalArgumentException("recipeListViewModel construction failed")
        }
    }

}