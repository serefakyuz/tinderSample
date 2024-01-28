package com.serefakyuz.tindersample.api

import retrofit2.http.GET

interface ApiService {

    @GET("/character")
    fun getCharacterList()
}