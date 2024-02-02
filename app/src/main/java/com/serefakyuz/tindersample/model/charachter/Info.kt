package com.serefakyuz.tindersample.model.charachter


import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Info(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: String,
    @Json(name = "pages")
    val pages: Int,
    @Json(name = "prev")
    val prev: String?
){
    fun getNextPage(): Int?{
        return try {
            val uri = Uri.parse(next)
            uri.getQueryParameter("page")?.toInt()?.let {
                if(it > count){
                    null
                }
                it
            }
        }catch (ex: Exception){
            null
        }
    }
}