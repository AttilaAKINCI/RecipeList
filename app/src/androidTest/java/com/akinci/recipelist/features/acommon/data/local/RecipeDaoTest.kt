package com.akinci.recipelist.features.acommon.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.akinci.recipelist.di.TestModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
@ExperimentalCoroutinesApi
class RecipeDaoTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @TestModule.TestDB
    lateinit var db: RecipeDatabase

    private lateinit var recipeDao: RecipeDAO

    @Before
    fun setup() {
        hiltRule.inject()
        recipeDao = db.getRecipeDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertRecipeAndGetAllRecipes() = runBlockingTest {
        val recipes = listOf(
                RecipeEntity(
                   0L,
                    "1a22s",
                    "Deneme Recipe",
                    "https://deneme.io",
                    "Description deneme",
                    100.0,
                    "Akıncı",
                    "Super"
                )
            )

        recipeDao.insertAll(recipes)

        val allRecipes = recipeDao.getAllRecipes()
        assertThat(allRecipes[0].contentId).isEqualTo(recipes[0].contentId)

        val getSameRecipe = recipeDao.getRecipe("1a22s")
        assertThat(allRecipes[0].contentId).isEqualTo(getSameRecipe.contentId)
    }

    @Test
    fun insertRecipeAndGetSameRecipe() = runBlockingTest {
        val recipes = listOf(
            RecipeEntity(
                0L,
                "1a22s",
                "Deneme Recipe",
                "https://deneme.io",
                "Description deneme",
                100.0,
                "Akıncı",
                "Super"
            )
        )

        recipeDao.insertAll(recipes)

        val getSameRecipe = recipeDao.getRecipe("1a22s")
        assertThat(getSameRecipe.contentId).isEqualTo(recipes[0].contentId)
    }

    @Test
    fun insertRecipeAndCheckWithTitle() = runBlockingTest {
        val recipes = listOf(
            RecipeEntity(
                0L,
                "1a22s",
                "Deneme Recipe",
                "https://deneme.io",
                "Description deneme",
                100.0,
                "Akıncı",
                "Super"
            )
        )

        recipeDao.insertAll(recipes)

        val getSameRecipe = recipeDao.getRecipeWithTitle("Deneme Recipe")
        assertThat(getSameRecipe.contentId).isEqualTo(recipes[0].contentId)
    }

    @Test
    fun insertRecipeAndDelete() = runBlockingTest {
        val recipes = listOf(
            RecipeEntity(
                0L,
                "1a22s",
                "Deneme Recipe",
                "https://deneme.io",
                "Description deneme",
                100.0,
                "Akıncı",
                "Super"
            )
        )

        recipeDao.insertAll(recipes)

        recipeDao.deleteAll()

        val getSameRecipe = recipeDao.getRecipe("1a22s")
        assertThat(getSameRecipe).isNull()
    }

}