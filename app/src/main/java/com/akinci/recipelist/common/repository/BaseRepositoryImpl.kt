package com.akinci.recipelist.common.repository

import com.akinci.recipelist.common.helper.Resource
import com.akinci.recipelist.common.network.NetworkChecker
import timber.log.Timber
import java.lang.Exception

open class BaseRepositoryImpl constructor(
    private val networkChecker: NetworkChecker
) {
    /** Service generic network checker **/

    // CallService generify for JSON Responses
    suspend fun <T> callService(
        contentFulServiceAction : suspend () -> T,
        localServiceAction : suspend () -> T,
        localBackupAction : suspend (T) -> Unit
    ) : Resource<T> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                // internet connection is established.
                // invoke service generic part.
                val response = contentFulServiceAction.invoke()
                localBackupAction.invoke(response)

                Resource.Success(response)
            }else{
                // not connected to internet fetch local data
                Resource.Success(localServiceAction.invoke())
            }
        }catch (ex: Exception){
            Timber.d(ex)
            Resource.Error("UnExpected Service Exception.")
        }
    }
}