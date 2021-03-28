package com.akinci.recipelist.features.detail.viewmodel

import com.akinci.recipelist.common.ahelpers.InstantExecutorExtension
import com.akinci.recipelist.common.ahelpers.TestContextProvider
import com.akinci.recipelist.features.acommon.data.output.RecipeResponse
import com.akinci.recipelist.features.acommon.data.testoutput.RecipeTestResponse
import com.akinci.recipelist.features.detail.repository.RecipeDetailRepository
import com.akinci.recipelist.jsonresponses.GetRecipeList
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class RecipeDetailViewModelTest {

    @MockK
    lateinit var recipeDetailRepository: RecipeDetailRepository

    lateinit var recipeDetailViewModel : RecipeDetailViewModel

    private val coroutineContext = TestContextProvider()
    private val moshi = Moshi.Builder().build()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        recipeDetailViewModel = RecipeDetailViewModel(coroutineContext, recipeDetailRepository)
    }

    @AfterEach
    fun tearDown() { unmockkAll() }

    @Test
    fun `fetch Recipe Returns selected recipe`() = runBlockingTest {
        val testResponse = moshi.adapter(RecipeTestResponse::class.java).fromJson(GetRecipeList.recipeList)?.recipeList?.get(0)
        coEvery { recipeDetailRepository.fetchRecipe("4dT8tcb6ukGSIg2YyuGEOm") } returns testResponse?.let {
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
                })
        }!!

        recipeDetailViewModel.recipe.observeForever {
            assertThat(it.title).isEqualTo("White Cheddar Grilled Cheese with Cherry Preserves & Basil")
        }

        val recipe = recipeDetailRepository.fetchRecipe("4dT8tcb6ukGSIg2YyuGEOm")

        assertThat(recipe.title).isEqualTo("White Cheddar Grilled Cheese with Cherry Preserves & Basil")

    }
}