package com.example.rickandmortydb.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("/api/character/")
    suspend fun searchCharacters(
        @Query("name") name: String,
        @Query("page") page: String?
    ) : SearchDto

    @GET("/api/character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: String
    ) : List<CharacterDto>
}