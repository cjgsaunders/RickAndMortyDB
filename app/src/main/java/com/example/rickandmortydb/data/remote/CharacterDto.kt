package com.example.rickandmortydb.data.remote

import com.squareup.moshi.Json

data class CharacterDto(
    @field:Json(name = "id")
    val characterId: Int,
    @field:Json(name = "name")
    val characterName: String,
    @field:Json(name = "image")
    val characterImage: String,
    @field:Json(name = "url")
    val characterUrl: String,
    val status: String,
    val species: String,
    @field:Json(name = "location")
    val lastLocation: LocationDto,
    @field:Json(name = "origin")
    val firstLocation: OriginDto,
    val episode: List<String>)


data class LocationDto(
    val name: String
)

data class OriginDto(
    val name: String
)
