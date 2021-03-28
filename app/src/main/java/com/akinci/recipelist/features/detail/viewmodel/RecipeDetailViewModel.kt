package com.akinci.recipelist.features.detail.viewmodel

import androidx.lifecycle.*
import com.akinci.recipelist.common.coroutines.CoroutineContextProvider
import com.akinci.recipelist.features.detail.repository.RecipeDetailRepository
import com.akinci.recipelist.features.acommon.data.output.RecipeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val coroutineContext : CoroutineContextProvider,
    private val recipeDetailRepository : RecipeDetailRepository
) : ViewModel() {

    /**
     *  listContent hold data that shown in recipe list fragment
     * **/
    private val _recipe = MutableLiveData<RecipeResponse>()
    val recipe : LiveData<RecipeResponse> = _recipe

    init {
        Timber.d("RecipeDetailViewModel created..")
    }

    fun fetchRecipe(contentId: String) = viewModelScope.launch(coroutineContext.IO) {
        Timber.tag("fetchRecipe-VMScope").d("Top-level: current thread is ${Thread.currentThread().name}")

        val recipeResponse = recipeDetailRepository.fetchRecipe(contentId)

        withContext(Dispatchers.Main) {
            _recipe.value = recipeResponse
        }

    }
}