package com.akinci.recipelist

import com.akinci.recipelist.commons.data.coroutines.Status
import com.akinci.recipelist.features.recipes.recipeListing.data.api.ContentHandler
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeDAO
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeDatabase
import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDASpace
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class ContentTest {

    private lateinit var contentHandler: ContentHandler
    private lateinit var cda: CDAClient

    @Before
    fun setup() {
        cda = mock(CDAClient::class.java, "deliveryClient")
        `when`(cda.fetchSpace()).thenReturn(mock(CDASpace::class.java))

        contentHandler = ContentHandler()
    }

    @Test
    fun checkFetchAllRecipes() {
        val result = contentHandler.fetchAllRecipes()

        when(result.status){
            Status.SUCCESS ->{
                assertNotEquals(null, result.data, "data request returned null List")
                assertNotEquals(0, result.data?.count(), "data request returned empty List")
            }
            Status.ERROR ->{ assertFalse(true, "data couldn't be fetched from contentful API") }
        }

        assertTrue(true, "checkFetchAllRecipes - test passed")
    }

    @Test
    fun sampleTest() { // sample test case
        Assert.assertEquals(4, 2 + 2)
    }

}