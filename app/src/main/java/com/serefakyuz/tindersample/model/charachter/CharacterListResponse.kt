package com.serefakyuz.tindersample.model.charachter


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterListResponse(
    @Json(name = "info")
    val info: Info?,
    @Json(name = "results")
    val results: List<CharacterItem?>?
)