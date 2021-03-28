package com.akinci.recipelist.features.listing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.recipelist.common.coroutines.CoroutineContextProvider
import com.akinci.recipelist.common.helper.Resource
import com.akinci.recipelist.features.acommon.data.output.RecipeResponse
import com.akinci.recipelist.features.listing.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val coroutineContext : CoroutineContextProvider,
    private val recipeRepository : RecipeRepository
) : ViewModel() {

    /**
     *  listContent hold data that shown in recipe list fragment
     * **/
    private val _listContent = MutableLiveData<Resource<List<RecipeResponse>>>()
    val listContent : LiveData<Resource<List<RecipeResponse>>> = _listContent

    init {
        Timber.d("RecipeListViewModel created..")
    }

    fun fetchAllRecipes() {
        // if listContent is fetched for first time
        if(_listContent.value == null){
            viewModelScope.launch(coroutineContext.IO){
                Timber.tag("fetchAllRecipes-VMScope").d("Top-level: current thread is ${Thread.currentThread().name}")

                // trigger shimmer view
                _listContent.postValue(Resource.Loading())

                when(val recipeResponse =  recipeRepository.fetchAllRecipes()) {
                    is Resource.Success -> {
                        // recipe response fetched
                        //simulate network delay for shimmer view.
                        delay(1000)

                        recipeResponse.data?.let {
                            _listContent.postValue(Resource.Success(recipeResponse.data))
                        }
                    }
                    is Resource.Error -> {
                        // error occurred while fetching followers
                        _listContent.postValue(Resource.Error(recipeResponse.message))
                    }
                }

            }
        }
    }


}
