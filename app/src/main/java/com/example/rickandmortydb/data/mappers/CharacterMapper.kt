package com.example.rickandmortydb.data.mappers

import com.example.rickandmortydb.data.remote.CharacterDto
import com.example.rickandmortydb.domain.objects.CharacterInfo

fun CharacterDto.toCharacter() : CharacterInfo {
    return CharacterInfo(
        characterId = characterId,
        characterName = characterName,
        characterImage = characterImage,
        characterUrl = characterUrl,
        status = status,
        species = species,
        lastLocation = lastLocation.name,
        firstLocation = firstLocation.name
    )
}