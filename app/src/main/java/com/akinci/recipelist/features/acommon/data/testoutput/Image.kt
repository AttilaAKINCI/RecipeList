package com.akinci.recipelist.features.acommon.data.testoutput

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val height: Int,
    val width: Int
)