package com.akinci.recipelist.features.recipes.recipeListing.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akinci.recipelist.features.recipes.recipeListing.data.local.DBConstants.Companion.DB_NAME

@Database( entities = [RecipeEntity::class], version = 2, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract val recipeDAO: RecipeDAO

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}