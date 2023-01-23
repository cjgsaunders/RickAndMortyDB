package com.example.rickandmortydb.domain.objects

data class CharacterInfo(
    val characterId: Int,
    val characterName: String,
    val characterImage: String,
    val characterUrl: String,
    val status: String,
    val species: String,
    val lastLocation: String,
    val firstLocation:String
)
