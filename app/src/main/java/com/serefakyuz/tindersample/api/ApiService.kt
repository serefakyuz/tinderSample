package com.serefakyuz.tindersample.api

import com.serefakyuz.tindersample.model.charachter.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/character")
    suspend fun getCharacterList(
        @Query("page") page: Int
    ): Response<CharacterListResponse>
}