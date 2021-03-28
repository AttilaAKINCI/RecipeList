package com.akinci.recipelist.features.acommon.data.testoutput

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class File(
    val contentType: String,
    val details: Details,
    val fileName: String,
    val url: String
)