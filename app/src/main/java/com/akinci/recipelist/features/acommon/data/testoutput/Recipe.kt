package com.akinci.recipelist.features.acommon.data.testoutput

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    val contentId:String,
    val calories: Int,
    val chef: Chef?,
    val description: String,
    val photo: Photo,
    val tags: List<Tag>?,
    val title: String
)