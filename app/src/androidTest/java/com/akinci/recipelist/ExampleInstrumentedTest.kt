package com.akinci.recipelist

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.akinci.recipelist.features.acommon.data.local.RecipeDAO
import com.akinci.recipelist.features.acommon.data.local.RecipeDatabase
import com.akinci.recipelist.features.acommon.data.local.RecipeEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var context : Context
    private lateinit var dao : RecipeDAO
    private lateinit var db: RecipeDatabase

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setup(){
        context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java).build()
        dao = db.recipeDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDb() { db.close() }

    @Test
    fun checkDBInsert() = runBlocking<Unit>  {
        val inputRecipe = RecipeEntity("Meat Soup","image_URL","Delicious soup ever", 10.0, "Mr. Akıncı","soup;healty;meat")
        dao.insert(inputRecipe)

        val outputRecipe = dao.getRecipeWithTitle("Meat Soup")

        assertNotEquals("Recipe not founded",null, outputRecipe)
        assertEquals("Recipe Titles Not equal",inputRecipe.title, outputRecipe.title)
        assertEquals("Recipe ImgUrl Not equal",inputRecipe.imageUrl, outputRecipe.imageUrl)
        assertEquals("Recipe calories Not equal",inputRecipe.calories, outputRecipe.calories, 0.5)
        assertEquals("Recipe chef name Not equal",inputRecipe.chefName, outputRecipe.chefName)
        assertEquals("Recipe tags Not equal",inputRecipe.tags, outputRecipe.tags)

        assertTrue("Room Database - test passed.",true)
    }

    @Test
    fun checkAppContext() {
        // Context of the app under test.
        assertEquals("com.akinci.recipelist", context.packageName)
    }
}