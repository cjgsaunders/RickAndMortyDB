package com.example.rickandmortydb.data.mappers

import com.example.rickandmortydb.data.remote.CharacterDto
import com.example.rickandmortydb.data.remote.SearchDto
import com.example.rickandmortydb.domain.objects.CharacterInfo
import com.example.rickandmortydb.domain.objects.SearchResult

fun SearchDto.toSearchResult(): SearchResult {
    val transform: (CharacterDto) -> CharacterInfo = {it.toCharacter()}
    val results = results.map(transform)

    return SearchResult(
        totalResults = metaData.count,
        nextPage = metaData.nextPage,
        result = results
    )
}

fun  List<CharacterDto>.listToSearchResult(): SearchResult {
    val transform: (CharacterDto) -> CharacterInfo = {it.toCharacter()}
    val results = this.map(transform)

    return SearchResult(
        totalResults = 20,
        nextPage = null,
        result = results
    )
}



