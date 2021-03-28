package com.akinci.recipelist.di

import android.content.Context
import androidx.room.Room
import com.akinci.recipelist.features.acommon.data.local.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TestDB

    @Provides
    @TestDB
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context, RecipeDatabase::class.java
        ).allowMainThreadQueries()
            .build()
}