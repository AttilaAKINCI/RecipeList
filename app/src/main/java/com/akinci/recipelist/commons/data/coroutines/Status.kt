package com.akinci.recipelist.commons.data.coroutines

/** Keeps network request status during kotlin coroutine calls **/
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}