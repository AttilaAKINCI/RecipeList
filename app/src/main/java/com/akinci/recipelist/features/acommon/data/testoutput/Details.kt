package com.akinci.recipelist.features.acommon.data.testoutput

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Details(
    val image: Image,
    val size: Int
)