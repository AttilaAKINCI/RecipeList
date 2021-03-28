package com.akinci.recipelist.features.detail.di

import com.akinci.recipelist.common.network.NetworkChecker
import com.akinci.recipelist.features.detail.repository.RecipeDetailRepository
import com.akinci.recipelist.features.acommon.data.local.RecipeDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {

    @Provides
    @Singleton
    fun provideRecipeDetailRepository(
        localService: RecipeDAO,
        networkChecker: NetworkChecker
    ) = RecipeDetailRepository(localService, networkChecker)

}