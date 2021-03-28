package com.akinci.recipelist.features.acommon.data.testoutput

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    val file: File,
    val title: String
)