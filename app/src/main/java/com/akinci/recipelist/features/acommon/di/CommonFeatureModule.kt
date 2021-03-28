package com.akinci.recipelist.features.acommon.di

import android.content.Context
import androidx.room.Room
import com.akinci.recipelist.common.room.RoomConfig.Companion.DB_NAME
import com.akinci.recipelist.features.acommon.data.local.RecipeDatabase
import com.akinci.recipelist.features.acommon.data.local.RecipeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonFeatureModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            RecipeDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideRecipeDao(db: RecipeDatabase) = db.getRecipeDao()

    @Provides
    @Singleton
    fun provideRecipeMapper() = RecipeMapper()

}