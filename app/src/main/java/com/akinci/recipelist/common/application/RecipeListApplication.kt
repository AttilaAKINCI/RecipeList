package com.akinci.recipelist.common.application

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.akinci.recipelist.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RecipeListApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        /**
         * Timber.i(...) -> INFO
         * Timber.w(...) -> WARNING
         * Timber.e(...) -> ERROR
         *
         * **/

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }else{
            Timber.plant(FireBaseCrashlyticsTree())
        }

        /**
         * INITIALIZATION FOR FIREBASECRASHLYTICS
         * **/

    }

    class FireBaseCrashlyticsTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            when(priority){ Log.VERBOSE, Log.DEBUG -> return }
            /**
             * THROW FIREBASECRASHLYTICS EXCEPTION
             * **/
        }
    }
}