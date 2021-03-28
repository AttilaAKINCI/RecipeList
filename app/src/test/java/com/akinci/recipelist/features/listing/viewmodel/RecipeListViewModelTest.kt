package com.akinci.recipelist.features.listing.viewmodel

import com.akinci.recipelist.common.ahelpers.InstantExecutorExtension
import com.akinci.recipelist.common.ahelpers.TestContextProvider
import com.akinci.recipelist.common.helper.Resource
import com.akinci.recipelist.features.acommon.data.output.RecipeResponse
import com.akinci.recipelist.features.listing.repository.RecipeRepository
import com.akinci.recipelist.jsonresponses.GetRecipeList
import com.akinci.recipelist.features.acommon.data.testoutput.RecipeTestResponse
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class RecipeListViewModelTest {

    @MockK
    lateinit var recipeRepository: RecipeRepository

    lateinit var recipeListViewModel : RecipeListViewModel

    private val coroutineContext = TestContextProvider()
    private val moshi = Moshi.Builder().build()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        recipeListViewModel = RecipeListViewModel(coroutineContext, recipeRepository)
    }

    @AfterEach
    fun tearDown() { unmockkAll() }

    @Test
    fun `fetch all recipes called returns Resource Success`() {
        val testResponse = moshi.adapter(RecipeTestResponse::class.java).fromJson(GetRecipeList.recipeList)
        coEvery { recipeRepository.fetchAllRecipes() } returns Resource.Success(
            testResponse?.recipeList?.map {
                RecipeResponse(
                    0L,
                    it.contentId,
                    it.title,
                    it.photo.file.url,
                    it.description,
                    it.calories.toDouble(),
                    it.chef?.name,
                    it.tags?.map { tag ->
                        tag.name
                    }
                )
            }
        )

        recipeListViewModel.listContent.observeForever {
            when(it){
                is Resource.Loading -> { assertThat(true).isTrue() }
                is Resource.Success -> {
                    assertThat(it.data).isNotEmpty()
                    assertThat(it.data?.get(0)?.title).isEqualTo("White Cheddar Grilled Cheese with Cherry Preserves & Basil")
                    assertThat(it.data?.get(1)?.title).isEqualTo("Tofu Saag Paneer with Buttery Toasted Pita")
                    assertThat(it.data?.get(2)?.title).isEqualTo("Grilled Steak & Vegetables with Cilantro-Jalapeño Dressing")
                    assertThat(it.data?.get(3)?.title).isEqualTo("Crispy Chicken and Rice with Peas & Arugula Salad")
                }
            }
        }

        recipeListViewModel.fetchAllRecipes()

        coroutineContext.testCoroutineDispatcher.advanceUntilIdle()
    }

    @Test
    fun `fetch all recipes called returns Resource Error`() {
        coEvery { recipeRepository.fetchAllRecipes() } returns Resource.Error("fetchAllRecipes encountered an error")

        recipeListViewModel.listContent.observeForever {
            when(it){
                is Resource.Loading -> { assertThat(true).isTrue() }
                is Resource.Error -> {
                    assertThat(it.message).isEqualTo("fetchAllRecipes encountered an error")
                }
            }
        }

        recipeListViewModel.fetchAllRecipes()
    }

}