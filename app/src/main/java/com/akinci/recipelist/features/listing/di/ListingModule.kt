package com.akinci.recipelist.features.listing.di

import com.akinci.recipelist.BuildConfig
import com.akinci.recipelist.common.di.AppModule
import com.akinci.recipelist.common.network.NetworkChecker
import com.akinci.recipelist.features.listing.data.api.ContentService
import com.akinci.recipelist.features.acommon.data.local.RecipeDAO
import com.akinci.recipelist.features.acommon.data.local.RecipeMapper
import com.akinci.recipelist.features.listing.repository.RecipeRepository
import com.contentful.java.cda.CDAClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListingModule {

    @Provides
    @Singleton
    fun provideCDAClient(
        @AppModule.BaseURL baseUrl: String
    ): CDAClient = CDAClient.builder().apply {
        setSpace(BuildConfig.CDA_SPACE)
        setToken(BuildConfig.CDA_TOKEN)
        setEndpoint(baseUrl)
    }.build()

    @Provides
    @Singleton
    fun provideCDAService(
        client : CDAClient,
        recipeMapper: RecipeMapper
    ) = ContentService(client, recipeMapper)

    @Provides
    @Singleton
    fun provideRecipeRepository(
        localService: RecipeDAO,
        remoteService: ContentService,
        networkChecker: NetworkChecker
    ) = RecipeRepository(localService, remoteService, networkChecker)

}